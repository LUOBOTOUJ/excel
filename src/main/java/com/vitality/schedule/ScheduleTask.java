package com.vitality.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vitality.enums.TaskStatus;
import com.vitality.material.entity.Cut;
import com.vitality.material.entity.InventoryMove;
import com.vitality.material.mapper.InventoryMoveMapper;
import com.vitality.material.service.ICutService;
import com.vitality.material.service.IInventoryAdjustmentService;
import com.vitality.material.service.IInventoryMoveService;
import com.vitality.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

    @Scheduled(cron = "0/5 * * * * ?")  //定时任务时间设置 s m h d m y
    @Async
    public void scheduledInventoryMove(){

        log.info("库存转移定时任务开启"+LocalDateTime.now().toString());

        QueryWrapper queryWrapper = new QueryWrapper<InventoryMove>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("moveDate");
        List<InventoryMove> inventoryMoveList = service.list(queryWrapper);

        if (0 !=inventoryMoveList.size()) {
            InventoryMove inventoryMove = inventoryMoveList.get(0);

            HttpClientUtils httpClientUtils = new HttpClientUtils();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/interTransfer?actionType=warehouseAllocation";

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

            String token = null;
            if (httpClientUtils.instanceTargetToken()) {
                token = httpClientUtils.getAccessToken();
            } else {
                log.error("token获取失败");
            }

            try {
                log.info("params:" + params.toString());
                String result = httpClientUtils.doPost(url, token, params);
                if (result.contains("操作成功")) {
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

    /*@Scheduled(cron = "0/5 * * * * ?")  //定时任务时间设置 s m h d m y
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

            Map params = new HashMap<>();
            params.put("inventory", inventoryAdjust.getInventory());
            params.put("materialCode", inventoryAdjust.getMaterialCode());
            params.put("meter", inventoryAdjust.getAdjustQuantity());
            params.put("recordUuid", inventoryAdjust.getId());
            params.put("subInventory", inventoryAdjust.getSubInv());
            params.put("uuid", uuid);
            params.put("validDate", inventoryAdjust.getAdjustDate().toString());
            params.put("batchNo", inventoryAdjust.getHjBatchNumber());

            String token = null;
            if (httpClientUtils.instanceTargetToken()) {
                token = httpClientUtils.getAccessToken();
            } else {
                log.error("token获取失败");
            }

            try {
                log.info("params:" + params.toString());
                String result = httpClientUtils.doPost(url, token, params);
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
    }*/
}