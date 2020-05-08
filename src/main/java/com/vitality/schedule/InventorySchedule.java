package com.vitality.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vitality.enums.TaskStatus;
import com.vitality.material.entity.Cut;
import com.vitality.material.entity.CutDetail;
import com.vitality.material.entity.InventoryMove;
import com.vitality.material.mapper.CutMapper;
import com.vitality.material.mapper.InventoryMoveMapper;
import com.vitality.material.service.ICutService;
import com.vitality.material.service.IInventoryMoveService;
import com.vitality.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


@EnableScheduling
@Component
@EnableAsync
public class InventorySchedule {
    private static Logger log = LoggerFactory.getLogger(InventorySchedule.class);


    @Autowired
    private InventoryMoveMapper moveMapper;
    @Autowired
    private CutMapper cutMapper;
    @Autowired
    private IInventoryMoveService service;
    @Autowired
    private ICutService cutService;

    @Scheduled(cron = "0/60 * * * * ?")  //定时任务时间设置 s m h d m y
    @Async
    public void scheduledInventoryMove(){
        log.info("库存转移定时任务开启"+LocalDateTime.now().toString());
        QueryWrapper queryWrapper = new QueryWrapper<InventoryMove>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("moveDate");
        List<InventoryMove> inventoryMoveList = service.list(queryWrapper);
        InventoryMove inventoryMove = inventoryMoveList.get(0);

        if (null != inventoryMove){
            HttpClientUtils httpClientUtils = new HttpClientUtils();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/interTransfer?actionType=warehouseAllocation";
            Map params = new HashMap<>();
            params.put("inventory",inventoryMove.getToInventory());
            params.put("materialCode",inventoryMove.getMaterialCode());
            params.put("meter",inventoryMove.getMoveQuantity());
            params.put("outInventory",inventoryMove.getFromInventory());
            params.put("outSubInventory",inventoryMove.getFromSubInv());
            params.put("recordUuid",inventoryMove.getUuid());
            params.put("subInventory",inventoryMove.getToSubInv());
            params.put("uuid",uuid);
            params.put("validDate", inventoryMove.getMoveDate().toString());
            params.put("batchNo",inventoryMove.getBatchNumber());
            String token = null;
            if (httpClientUtils.instanceTargetToken()){
                token = httpClientUtils.getAccessToken();
            }else {
                log.error("token获取失败");
            }
            try{
                String result = httpClientUtils.doPost(url,token,params);
                if (result.contains("操作成功")){
                    System.out.println(result);
                    inventoryMove.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                    inventoryMove.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
                    moveMapper.updateStatus(inventoryMove);
                }else {
                    log.info("错误信息:" + result);
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0/60 * * * * ?")  //定时任务时间设置 s m h d m y
    @Async
    public void scheduledCut(){
        log.info("裁剪定时任务开启"+LocalDateTime.now().toString());
        QueryWrapper queryWrapper = new QueryWrapper<Cut>();
        queryWrapper.eq("status","N");
        queryWrapper.orderByAsc("create_date");
        List<Cut> cutList = cutService.list(queryWrapper);
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
            params.put("uuid",cutList.get(0).getExpCutTransId());
            List<CutDetail> newBatchList = new ArrayList();

            for (Cut cut:list){
                CutDetail cutDetail = new CutDetail();
                cutDetail.setBatchNo(cut.getNewLotNumber());
                cutDetail.setInventory(cut.getNewInventory());
                cutDetail.setSubInventory(cut.getNewSubInventory());
                cutDetail.setQuantity(cut.getNewQty());
                cutDetail.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
                newBatchList.add(cutDetail);
            }
            params.put("newBatchList",newBatchList);
            HttpClientUtils httpClientUtils = new HttpClientUtils();
            String url = "https://api.vitalitytex.com.cn/inventory/inventory/v1/cut?actionType=warehouseAllocation";
            String token = null;
            if (httpClientUtils.instanceTargetToken()){
                token = httpClientUtils.getAccessToken();
            }else {
                log.error("token获取失败");
            }
            try{
                String result = httpClientUtils.doPost(url,token,params);
                if (result.contains("操作成功")){
                    for (Cut cut:list){
                        Cut cut1 = new Cut();
                        BeanUtils.copyProperties(cut,cut1);
                        cut1.setStatus(TaskStatus.DELETE_STATUS_NO.val());
                        cut1.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
                        cutMapper.updateStatus(cut1);
                    }
                }else {
                    log.info("错误信息:" + result);
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }




}