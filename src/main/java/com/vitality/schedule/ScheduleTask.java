package com.vitality.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vitality.enums.TaskStatus;
import com.vitality.material.entity.Cut;
import com.vitality.material.entity.InventoryAdjust;
import com.vitality.material.entity.InventoryMove;
import com.vitality.material.mapper.InventoryMoveMapper;
import com.vitality.material.mapper.PoMapper;
import com.vitality.material.service.ICutService;
import com.vitality.material.service.IInventoryAdjustmentService;
import com.vitality.material.service.IInventoryMoveService;
import com.vitality.utils.HttpClientUtils;
import org.hibernate.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public  class ScheduleTask {
    private static Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    @Resource
    private PoMapper poMapper;
    @Autowired
    private InventoryMoveMapper moveMapper;
    @Autowired
    private IInventoryAdjustmentService adjustmentService;
    @Autowired
    private IInventoryMoveService service;
    @Autowired
    private ICutService cutService;

    private static final String Authorization = "Authorization";

    private String accessToken = null;

    //定时任务时间设置 s m h d m y

    /**
     * 库存转移
     * 根据时间顺序排序
     * 优先处理状态为N，即未处理的数据
     * 处理完状态置为P
     *
     * @author 江越天
     * @date 2020-05-11 11:53
     * @param //
     * 每五秒执行一次
     * @Asyc异步执行的注解
     * 定时任务时间设置 s m h d m y
     * @return void
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void scheduledInventoryMove(){

        log.info("库存转移定时任务开启"+LocalDateTime.now().toString());

        /**
         * 采用Mybatis-Plus的QueryWapper来处理数据库操作
         * 注明实体类
         */
        QueryWrapper queryWrapper = new QueryWrapper<InventoryMove>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("moveDate");
        List<InventoryMove> inventoryMoveList = service.list(queryWrapper);

        /**
         * 判断第一条数据是否存在
         * 如不存在，则输出日志
         */
        if (0 !=inventoryMoveList.size()) {
            InventoryMove inventoryMove = inventoryMoveList.get(0);

            /**
             * new一个调用第三方接口的工具类
             */
            HttpClientUtils httpClientUtils = new HttpClientUtils();
            /**
             * 生成uuid
             * 去掉"-"
             */
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            /**
             * 第三方接口路径
             * query参数从路径中传
             */
            String url = "https://api.vitalitytex.com.cn/voucher/voucher/v1/vouchers";


            /**
             * 构造传入接口的参数
             * 时间以sql.Date格式
             */
            Map params = new HashMap<>();
            params.put("uuid",uuid);
            params.put("voucherCode", inventoryMove.getUuid());
            params.put("voucherType", "StockMove");

            JSONObject object = new JSONObject();

            object.put("inventory", inventoryMove.getInventory());
            object.put("materialCode", inventoryMove.getMaterialCode());
            object.put("moveQty", inventoryMove.getMoveQuantity());
            object.put("subInventory", inventoryMove.getSubInv());
            object.put("recordUuid", inventoryMove.getUuid());
            object.put("moveDate", inventoryMove.getMoveDate().toString());
            params.put("moveType", inventoryMove.getMoveType());

            object.put("batchNo", inventoryMove.getBatchNumber());

            params.put("voucherInfo",object.toJSONString());

            /**
             * 调用工具类
             * 获取token
             * 此工具类中token为单例
             * 可全局访问
             */
            String token = null;
            if (httpClientUtils.instanceTargetToken()) {

                token = httpClientUtils.getAccessToken();
            } else {
                log.error("token获取失败");
            }

            try {
                log.info("params:" + params.toString());

                /**
                 * 调用工具类中Post请求
                 */
                String result = httpClientUtils.doPost(url, token, params);
                /**
                 * 进行接口调用判断是否成功，执行下一步
                 */
                if (result.contains("操作成功")) {
                    /**
                     *更新状态信息
                     */
                    inventoryMove.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                    inventoryMove.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
                    if (!moveMapper.updateStatus(inventoryMove)){
                        log.error("库存转移定时任务状态更新失败！");
                    }else {
                        log.info("库存转移操作成功"+params.toString());
                    }
                } else {
                    log.error("错误信息:" + result);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }else {
            log.info("暂无库存转移任务");
        }

    }

    /**
     *
     *
     * @author 江越天
     * @date 2020-05-14 14:25
     *  裁剪
     * @return void
     */
    @Scheduled(cron = "0/5 * * * * ?")  //定时任务时间设置 s m h d m y
    @Async
    public void scheduledCut(){
        log.info("裁剪定时任务开启"+LocalDateTime.now().toString());
        QueryWrapper queryWrapper = new QueryWrapper<Cut>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("create_date");
        List<Cut> cutList = cutService.list(queryWrapper);
        if (0 != cutList.size()){
            String oldLotNum = cutList.get(0).getSourceLotNumber();
            QueryWrapper queryWrapper1 = new QueryWrapper<Cut>();
            queryWrapper1.eq("source_lot_number",oldLotNum);
            queryWrapper1.eq("status","N");
            List<Cut> list = cutService.list(queryWrapper1);

            if (!list.isEmpty()){

                Map params = new HashMap<>();
                params.put("uuid", UUID.randomUUID().toString().replaceAll("-",""));
                params.put("voucherCode", cutList.get(0).getId());
                params.put("voucherType", "Cut");

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("batchNo",oldLotNum);
                jsonObject.put("inventory",cutList.get(0).getFromInventory());
                jsonObject.put("materialCode",cutList.get(0).getItemNumber());
                jsonObject.put("subInventory",cutList.get(0).getFromSubInventory());
                jsonObject.put("sourceQty",cutList.get(0).getSourceQty());

                List newBatchList = new ArrayList();
                list.forEach(cut->{
                    JSONObject object = new JSONObject();
                    object.put("newLotNumber",cut.getNewLotNumber());
                    object.put("newInventory",cut.getNewInventory());
                    object.put("newQuantity",cut.getNewQty());
                    object.put("newSubInventory",cut.getNewSubInventory());
                    newBatchList.add(object);
                });

                jsonObject.put("newBatchList",newBatchList);
                params.put("voucherInfo",jsonObject.toJSONString());

                HttpClientUtils httpClientUtils = new HttpClientUtils();
                String url = "https://api.vitalitytex.com.cn/voucher/voucher/v1/vouchers";
                String token = null;
                if (httpClientUtils.instanceTargetToken()){
                    token = httpClientUtils.getAccessToken();
                }else {
                    log.error("token获取失败");
                }

                try{
                    log.info("params:"+params.toString());
                    String result = httpClientUtils.doPost(url,token,params);
                    if (result.contains("操作成功")){
                        for (Cut cut:list){

                            Cut cut1 = new Cut();
                            BeanUtils.copyProperties(cut,cut1);
                            cut1.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                            cut1.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));

                            if (!cutService.updateById(cut1)){
                                log.error("裁剪定时任务状态更新失败！"+cut1.toString());
                            }else {
                                log.info("裁剪定时任务操作成功"+params.toString());
                            }

                        }
                    }else {
                        log.info("错误信息:" + result);
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }else {
                log.info("数据异常");
            }
        }else {
            log.info("暂无裁剪任务");
        }
    }

    /**
     *
     *
     * @author 江越天
     * @date 2020-05-14 14:25
     * 库存调整
     * @return void
     */
    //@Scheduled(cron = "0/5 * * * * ?")  //定时任务时间设置 s m h d m y
    @Async
    public void scheduledInventoryAdjust(){

        log.info("库存调整定时任务开启"+LocalDateTime.now().toString());

        QueryWrapper queryWrapper = new QueryWrapper<InventoryMove>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("adjustDate");
        List<InventoryAdjust> inventoryAdjustList = adjustmentService.list(queryWrapper);

        if (0 !=inventoryAdjustList.size()) {
            InventoryAdjust inventoryAdjust = inventoryAdjustList.get(0);

            HttpClientUtils httpClientUtils = new HttpClientUtils();
            //String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String url = "https://api.vitalitytex.com.cn/voucher/voucher/v1/vouchers";

            Map<String,Object> params = new HashMap<>();
            params.put("uuid", UUID.randomUUID().toString().replaceAll("-",""));
            params.put("voucherCode", inventoryAdjust.getId());
            params.put("voucherType", "StockAdjust");
            JSONObject object = new JSONObject();
            object.put("adjustDate",inventoryAdjust.getAdjustDate().toString());
            object.put("inventory",inventoryAdjust.getInventory());
            object.put("subInventory",inventoryAdjust.getSubInv());
            object.put("materialCode",inventoryAdjust.getMaterialCode());
            object.put("hjBatchNumber",inventoryAdjust.getHjBatchNumber());
            object.put("adjustQuantity",inventoryAdjust.getAdjustQuantity());
            params.put("voucherInfo",object.toJSONString());

            String token = null;
            if (instanceTargetToken()) {
                token = httpClientUtils.getAccessToken();
            } else {
                log.error("token获取失败");
            }

            //调用第三方接口

            try {
                log.info("params:" + params.toString());
                String result = httpClientUtils.doPost(url, token, params);
                if (result.contains("操作成功")) {
                    inventoryAdjust.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                    inventoryAdjust.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));

                    if (!adjustmentService.updateById(inventoryAdjust)){
                        log.error("库存调整定时任务状态更新失败！"+"params:"+inventoryAdjust);
                    }else {
                        log.info("库存调整操作成功"+params.toString());
                    }
                } else {
                    log.error("错误信息:" + result);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }else {
            log.info("暂无库存调整任务");
        }

    }

    public boolean instanceTargetToken() {
        if (accessToken == null) {
            try {
                //Map<String, String> taskConfigMap = configReaderUtil.loadConfigsByFile("task.properties");
                Map<String, String> targetParamsMap = new HttpClientUtils().getTargetParams();

                String writeToken = new HttpClientUtils().clientPost("https://sso.vitalitytex.com/auth/realms/vitality/protocol/openid-connect/token", targetParamsMap);
                JSONObject json = JSONObject.parseObject(writeToken);

                if (!json.containsKey("access_token")) {
                    log.info(writeToken);
                    return false;
                }
                //new HttpClientUtils().setAccessToken(json.getString("access_token"));
                log.info(accessToken);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     *
     *
     * @author 江越天
     * @date 2020-05-13 16:38
     * @param
     * @return 收货标签打印
     */
    //@Scheduled(cron = "0/3 * * * * ?")
    @Async
    public void receiptPrint(){
        StringBuffer poNum = new StringBuffer();
        StringBuffer lotNum = new StringBuffer();
        Map<String,Object> receiptMap = moveMapper.selectReceiptPrint();
        JSONObject printParam = JSONObject.parseObject(receiptMap.get("print_param").toString());
        if (printParam.containsKey("po_number")){
            poNum.append(printParam.get("po_number"));
        }else if(printParam.containsKey("lot_number")){
            lotNum.append(printParam.get("lot_number"));
        }
        log.info(poNum.toString());
        log.info(lotNum.toString());

    }

    @Scheduled(cron = "0/3 * * * * ?")
    @Async
    public void receiptInfo(){
        HashMap<String,Object> receiptInfo = poMapper.selectReceiptInfo();
        JSONObject object = new JSONObject();
        HashMap<String,Object> params = new HashMap<>();
        receiptInfo.forEach(
                (k,v)->{ object.put(k,v); }
                );
        if (!object.containsKey("receipt_ID") ||
                !object.containsKey("receipt_Date")||
                !object.containsKey("wh_id")||
                !object.containsKey("item_number")||
                !object.containsKey("po_number")||
                !object.containsKey("lot_number")||
                !object.containsKey("qty_received")||
                !object.containsKey("new_lot_number")){
            log.info("收货订单信息不全"+receiptInfo);
        }
        params.put("uuid", UUID.randomUUID().toString().replaceAll("-",""));
        params.put("voucherCode", object.getString("exp_receipt_id"));
        params.put("voucherType", "StockTransferIn");
        params.put("voucherInfo",object.toJSONString());
        String token = "abc";

        //调用第三方接口
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        String url = "https://api.vitalitytex.com.cn/voucher/voucher/v1/vouchers";
        try {
            log.info("params:" + params.toString());
            String result = httpClientUtils.doPost(url, token, params);
            if (result.contains("操作成功")) {
                poMapper.updateReceiptStatus(Integer.parseInt(object.get("exp_receipt_id").toString()));
            } else {
                log.error("错误信息:" + result);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}