package com.acong.ssoserver.material.controller;

import com.acong.ssoserver.enums.CodeEnum;
import com.acong.ssoserver.enums.DeleteStatus;
import com.acong.ssoserver.material.dto.MaterialRequest;
import com.acong.ssoserver.material.entity.YlwMaterial;
import com.acong.ssoserver.material.mapper.YlwMaterialMapper;
import com.acong.ssoserver.material.service.IYlwMaterialService;
import com.acong.ssoserver.utils.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
* <p>
    * 物料表 前端控制器
    * </p>
*
* @author Author
* @since 2020-04-14
*/
@Api(tags = "物料接口")
@RestController
@RequestMapping("/api")
public class YlwMaterialController {

    @Autowired
    private IYlwMaterialService materialService;
    @Autowired
    private YlwMaterialMapper materialMapper;

    @PostMapping("/material/add")
    @ApiOperation("新增物料")
    public ResultUtil add(@RequestBody @Validated MaterialRequest request) {
        YlwMaterial material = new YlwMaterial();
        BeanUtils.copyProperties(request,material);
        material.setDeleteStatus(DeleteStatus.DELETE_STATUS_NO.val());
        material.setCreateTime(LocalDateTime.now().toString());
        if (materialMapper.insertReturnId(material) != 1){
            return ResultUtil.fail("500","失败，请重试");
        }
        return ResultUtil.success(material,"添加成功");
    }

    @PostMapping("/material/update")
    @ApiOperation("修改物料信息")
    public ResultUtil update(@RequestBody MaterialRequest request){
        YlwMaterial temp = materialService.getById(request.getId());
        BeanUtils.copyProperties(request,temp);

        if (temp.getDeleteStatus() == DeleteStatus.DELETE_STATUS_YES.val()){
            return ResultUtil.fail("500","已删除物料无法更新");
        }
        BeanUtils.copyProperties(request,temp);
        temp.setUpdateTime(LocalDateTime.now().toString());
        if (!materialService.updateById(temp)){
            return ResultUtil.fail(CodeEnum.ERROR.val(),CodeEnum.ERROR.msg());
        }
        return ResultUtil.success(temp,"更新成功");
    }

    @DeleteMapping("/material/delete/{id}")
    @ApiOperation("删除物料信息")
    public ResultUtil delete(@NotNull(message = "请选择") @PathVariable("id")Integer id){
        YlwMaterial temp = materialService.getById(id);
        if (temp.getDeleteStatus() == DeleteStatus.DELETE_STATUS_YES.val()){
            return ResultUtil.fail("500","该物料已删除，无法重复删除",temp);
        }
        if (temp != null){
            temp.setDeleteStatus(DeleteStatus.DELETE_STATUS_YES.val());
        }
        if (!materialService.updateById(temp)){
            return ResultUtil.fail(CodeEnum.ERROR.val(),CodeEnum.ERROR.msg());
        }
        return ResultUtil.success(temp,"删除成功");
    }

    @GetMapping("/material/detail/{id}")
    @ApiOperation("查询一个物料信息")
    public ResultUtil getOne(@NotNull(message = "请选择") @PathVariable("id")Integer id){
        QueryWrapper<YlwMaterial> queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleteStatus",DeleteStatus.DELETE_STATUS_NO.val());
        queryWrapper.eq("id",id);
        YlwMaterial temp = materialService.getOne(queryWrapper);
        return ResultUtil.success(temp,"查询成功");
    }

    @PostMapping("/material/all")
    @ApiOperation("分页查询全部信息")
    public ResultUtil getAll(@RequestBody(required = false) MaterialRequest request){
        if (request.toString() != "" &&request.getCurrentPage() != null && request.getCurrentPage() != null){
            IPage<YlwMaterial> materialIPage = new Page<>(request.getCurrentPage(),request.getNum());
            QueryWrapper<YlwMaterial> queryWrapper = new QueryWrapper();
            queryWrapper.eq("deleteStatus",DeleteStatus.DELETE_STATUS_NO.val());
            materialIPage = materialMapper.selectPage(materialIPage,queryWrapper);
            materialIPage.isSearchCount();
            return ResultUtil.success(materialIPage,"查询成功");
        }
        return ResultUtil.fail("500","请输入currentPage和num");
    }
}

