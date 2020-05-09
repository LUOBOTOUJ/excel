package com.vitality.material.controller;

import com.vitality.material.dto.PoDetailRequest;
import com.vitality.material.dto.PoRequest;
import com.vitality.material.entity.Po;
import com.vitality.material.entity.PoDetail;
import com.vitality.material.mapper.PoMapper;
import com.vitality.material.service.IPoDetailService;
import com.vitality.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;


import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *  前端控制器
 *
 * @author Jack J
 * @since 2020-04-21
 */

@Api(tags = "预收货单接口")
@RestController
@RequestMapping("/api")
public class PoController {

    @Autowired
    private PoMapper poMapper;
    @Autowired
    private IPoDetailService poDetailService;



    @PostMapping("/po/add")
    @ApiOperation("新增预收货单")
    public ResultUtil add(@RequestBody @Validated({Insert.class}) PoRequest request) {
        Po po = new Po();
        BeanUtils.copyProperties(request,po);
        po.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        if (poMapper.insertReturnId(po) != 1){
            return ResultUtil.fail("500","保存失败",po);
        }

        for (PoDetailRequest poDetailRequest:request.getPoDetailRequests()){
            PoDetail poDetail = new PoDetail();
            BeanUtils.copyProperties(poDetailRequest,poDetail);
            poDetail.setBelongId(po.getId());
            poDetail.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

            if (!poDetailService.save(poDetail)){
                return ResultUtil.fail("500","保存失败",poDetail);
            }

        }
        return ResultUtil.success(request,"添加成功");
    }
}

