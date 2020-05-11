package com.vitality.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vitality.enums.TaskStatus;
import com.vitality.material.entity.Cut;
import com.vitality.material.entity.InventoryAdjust;
import com.vitality.material.entity.InventoryMove;
import com.vitality.material.mapper.InventoryMoveMapper;
import com.vitality.material.service.ICutService;
import com.vitality.material.service.IInventoryAdjustmentService;
import com.vitality.material.service.IInventoryMoveService;
import com.vitality.utils.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public  class ScheduleTask {
    private static Logger log = LoggerFactory.getLogger(ScheduleTask.class);

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
    @Scheduled(cron = "0/5 * * * * ?")
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
            String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/interTransfer?actionType=warehouseAllocation";


            /**
             * 构造传入接口的参数
             * 时间以sql.Date格式
             */
            Map params = new HashMap<>();
            params.put("inventory", inventoryMove.getToInventory());
            params.put("materialCode", inventoryMove.getMaterialCode());
            params.put("meter", inventoryMove.getMoveQuantity());
            params.put("outInventory", inventoryMove.getFromInventory());
            params.put("outSubInventory", inventoryMove.getFromSubInv());
            params.put("recordUuid", inventoryMove.getUuid());
            params.put("subInventory", inventoryMove.getToSubInv());
            params.put("uuid", uuid);
            params.put("validDate", inventoryMove.getMoveDate().toString());
            params.put("batchNo", inventoryMove.getBatchNumber());

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
                    }
                } else {
                    log.error("错误信息:" + result);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
        log.info("暂无库存转移任务");
    }

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
                params.put("batchNo",oldLotNum);
                params.put("inventory",cutList.get(0).getFromInventory());
                params.put("materialCode",cutList.get(0).getItemNumber());
                params.put("subInventory",cutList.get(0).getFromSubInventory());
                params.put("uuid",cutList.get(0).getId());

                List newBatchList = new ArrayList();

                for (Cut cut:list){
                    JSONObject object = new JSONObject();
                    object.put("batchNo",cut.getNewLotNumber());
                    object.put("inventory",cut.getNewInventory());
                    object.put("quantity",cut.getNewQty());
                    object.put("subInventory",cut.getNewSubInventory());
                    object.put("uuid",UUID.randomUUID().toString().replaceAll("-",""));
                    newBatchList.add(object);
                }

                params.put("newBatchList",newBatchList);

                HttpClientUtils httpClientUtils = new HttpClientUtils();
                String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/cut";
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
                            }

                        }
                    }else {
                        log.info("错误信息:" + result);
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
            log.info("暂无裁剪任务");
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")  //定时任务时间设置 s m h d m y
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
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/interTransfer?actionType=warehouseAllocation";

            Map<String,Object> params = new HashMap<>();
            params.put("inventory", inventoryAdjust.getInventory());
            params.put("materialCode", inventoryAdjust.getMaterialCode());
            params.put("meter", inventoryAdjust.getAdjustQuantity());
            params.put("recordUuid", inventoryAdjust.getId());
            params.put("subInventory", inventoryAdjust.getSubInv());
            params.put("uuid", uuid);
            params.put("validDate", inventoryAdjust.getAdjustDate().toString());
            params.put("batchNo", inventoryAdjust.getHjBatchNumber());

            String token = null;
            if (instanceTargetToken()) {
                token = httpClientUtils.getAccessToken();
            } else {
                log.error("token获取失败");
            }

            //调用第三方接口

            try {
                log.info("params:" + params.toString());

                // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost httpPost = new HttpPost(url);
                // 创建json参数
                JSONObject jsonObject = new JSONObject();
                for (String key:params.keySet()){
                    jsonObject.put(key,params.get(key));
                }

                // 模拟表单
                //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                // 将user对象转换为json字符串，并放入entity中
                StringEntity entity = new StringEntity(jsonObject.toJSONString(), ContentType.APPLICATION_JSON);

                // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
                httpPost.setEntity(entity);

                httpPost.setHeader("Content-Type", "application/json;charset=utf8");
                httpPost.setHeader(Authorization, "Bearer " + token);
                // 响应模型
                CloseableHttpResponse response = null;
                String result = null;
                try {
                    // 由客户端执行(发送)Post请求
                    response = httpClient.execute(httpPost);
                    // 从响应模型中获取响应实体
                    HttpEntity responseEntity = response.getEntity();

                    //System.out.println("响应状态为:" + response.getStatusLine());
                    if (responseEntity != null) {
                        result = EntityUtils.toString(responseEntity);
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 释放资源
                        if (httpClient != null) {
                            httpClient.close();
                        }
                        if (response != null) {
                            response.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (result.contains("操作成功")) {
                    inventoryAdjust.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                    inventoryAdjust.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
                    if (!adjustmentService.updateById(inventoryAdjust)){
                        log.error("库存调整定时任务状态更新失败！");
                    }
                } else {
                    log.error("错误信息:" + result);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
        log.info("暂无库存调整任务");
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
}