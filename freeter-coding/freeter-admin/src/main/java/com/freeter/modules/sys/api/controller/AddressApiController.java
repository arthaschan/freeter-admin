package com.freeter.modules.sys.api.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freeter.common.utils.PageUtils;
import com.freeter.common.utils.R;
import com.freeter.common.validator.ValidatorUtils;
import com.freeter.modules.sys.entity.AddressEntity;
import com.freeter.modules.sys.service.AddressService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




/**
 * 
 *
 * @author liuqi
 * @email 363236211@qq.com
 * @date 2018-05-18 13:41:16
 */
@RestController
@RequestMapping("address/api")
@Api(tags="接口")
public class AddressApiController {
    @Autowired
    private AddressService addressService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("获取多个")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = addressService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("获取相应的")
    public R info(@PathVariable("id") Integer id){
        AddressEntity address = addressService.selectById(id);

        return R.ok().put("address", address);
    }

    /**
     * 保存
     */
    @PostMapping("/save/json")
    @ApiOperation("添加数据")
    public R saveJson(@RequestBody AddressEntity address){
    	ValidatorUtils.validateEntity(address);
        addressService.insert(address);

        return R.ok();
    }
    
    /**
     * 保存
     */
    @PostMapping("/save/form")
    @ApiOperation("添加数据")
    public R saveForm(AddressEntity address){
    	ValidatorUtils.validateEntity(address);
        addressService.insert(address);

        return R.ok();
    }

    /**
     * 修改（参数：json）
     */
    @PostMapping("/update/json")
    @ApiOperation("修改数据（参数：json格式）")
    public R updateJson(@RequestBody AddressEntity address){
        ValidatorUtils.validateEntity(address);
        addressService.updateAllColumnById(address);//全部更新
        
        return R.ok();
    }


    /**
     * 修改（参数：传统表单）
     */
    @PostMapping("/update/form")
    @ApiOperation("修改数据（参数表单格式）")
    public R updateForm(AddressEntity address){
        ValidatorUtils.validateEntity(address);
        addressService.updateAllColumnById(address);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除数据")
    public R delete(@RequestBody Integer[] ids){
        addressService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
