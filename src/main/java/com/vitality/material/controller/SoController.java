package com.vitality.material.controller;

import com.vitality.material.dto.SoDetailRequest;
import com.vitality.material.dto.SoRequest;
import com.vitality.material.entity.So;
import com.vitality.material.entity.SoDetail;
import com.vitality.material.mapper.SoMapper;
import com.vitality.material.service.ISoDetailService;
import com.vitality.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *  前端控制器
 *
 * @author Jack J
 * @since 2020-04-21
 */

@Api(tags = "销售单接口")
@RestController
@RequestMapping("/api")
public class SoController {

    @Autowired
    private SoMapper soMapper;
    @Autowired
    private ISoDetailService soDetailService;



    @PostMapping("/so/add")
    @ApiOperation("新增销售单")
    public ResultUtil add(@RequestBody @Validated({Insert.class}) SoRequest request) {
        So so = new So();
        BeanUtils.copyProperties(request,so);
        so.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        if (soMapper.insertReturnId(so) != 1){
            return ResultUtil.fail("500","保存失败",so);
        }
        for (SoDetailRequest soDetailRequest:request.getSoDetailRequests()){
            SoDetail soDetail = new SoDetail();
            BeanUtils.copyProperties(soDetailRequest,soDetail);
            soDetail.setBelongId(so.getId());
            soDetail.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            if (!soDetailService.save(soDetail)){
                return ResultUtil.fail("500","保存失败",soDetail);
            }
        }
        return ResultUtil.success(request,"添加成功");
    }
}

