package com.vitality.material.controller;

import com.vitality.material.dto.TranRequest;
import com.vitality.material.entity.Tran;
import com.vitality.material.service.ITranService;
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

@Api(tags = "转储单接口")
@RestController
@RequestMapping("/api")
public class TranController {

    @Autowired
    private ITranService tranService;



    @PostMapping("/tran/add")
    @ApiOperation("新增转储单")
    public ResultUtil add(@RequestBody @Validated({Insert.class}) TranRequest request) {
        Tran tran = new Tran();
        BeanUtils.copyProperties(request,tran);
        tran.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        if (!tranService.save(tran)){
            return ResultUtil.fail("500","转储单保存失败",request);
        }
        return ResultUtil.success(request,"添加成功");
    }
}

