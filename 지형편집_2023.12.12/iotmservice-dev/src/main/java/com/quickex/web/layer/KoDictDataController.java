package com.quickex.web.layer;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoDictData;
import com.quickex.service.layer.IKoDictDataService;
import com.quickex.service.layer.IKoDictTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/product/dict/data")
public class KoDictDataController extends BaseController {
    @Resource
    private IKoDictDataService dictDataService;

    @Resource
    private IKoDictTypeService dictTypeService;

    @PostMapping("/list")
    public R list(@RequestBody KoDictData dictData) {
        IPage<KoDictData> list = dictDataService.selectDictDataList(dictData,startPage());
        return R.success(list);
    }
    /**
     * Query dictionary data details
     */
    @GetMapping(value = "/{dictCode}")
    public R getInfo(@PathVariable String dictCode) {
        return R.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * Query dictionary data information according to dictionary type
     */
    @GetMapping(value = "/type/{dictType}")
    public R dictType(@PathVariable String dictType) {
        return R.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * New dictionary type
     */

    @PostMapping
    public R add(@Validated @RequestBody KoDictData dict) {
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * Modify save dictionary type
     */
    @PutMapping
    public R edit(@Validated @RequestBody KoDictData dict) {
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * Delete dictionary type
     */
    @DeleteMapping("/{dictCodes}")
    public R remove(@PathVariable String[] dictCodes) {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
