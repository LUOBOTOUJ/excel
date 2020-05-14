package com.vitality.material.controller;

import com.vitality.material.dto.ReserveRequest;
import com.vitality.material.entity.Reserve;
import com.vitality.material.service.IReserveService;
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

@Api(tags = "预留单接口")
@RestController
@RequestMapping("/api")
public class ReserveController {

    @Autowired
    private IReserveService reserveService;



    @PostMapping("/reserve/add")
    @ApiOperation("新增预留单")
    public ResultUtil add(@RequestBody @Validated({Insert.class}) ReserveRequest request) {
        Reserve reserve = new Reserve();
        BeanUtils.copyProperties(request,reserve);
        reserve.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        if (!reserveService.save(reserve)){
            return ResultUtil.fail("500","预留保存失败",request);
        }
        return ResultUtil.success(request,"添加成功");
    }
}

