package com.quickex.web.layer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.quickex.core.constant.UserConstant;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoDictType;
import com.quickex.service.layer.IKoDictTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/product/dict/type")
public class KoDictTypeController extends BaseController {
    @Resource
    private IKoDictTypeService dictTypeService;
    @GetMapping("/list")
    public R list(KoDictType dictType) {
        IPage<KoDictType> list = dictTypeService.selectDictTypeList(dictType,startPage());
        return R.success(list);
    }

    /**
     * Get dictionary drop-down tree list
     */
    @GetMapping("/treeselect")
    public R treeselect(KoDictType dictType) {
        List<KoDictType> list = dictTypeService.selectDictTypeList(dictType);
        return R.success(dictTypeService.buildDictTreeSelect(list));
    }

    /**
     * Query dictionary type details
     */
    @GetMapping(value = "/{dictId}")
    public R getInfo(@PathVariable String dictId) {
        return R.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * New dictionary type
     */
    @PostMapping
    public R add(@Validated @RequestBody KoDictType dict) {
        if (UserConstant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return R.error("add'" + dict.getDictName() + "'Failed，Dictionary type already exists");
        }
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * Modify dictionary type
     */
    @PutMapping
    public R edit(@Validated @RequestBody KoDictType dict) {
        if (UserConstant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return R.error("edit'" + dict.getDictName() + "'Failed，Dictionary type already exists");
        }

        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * Delete dictionary type
     */
    @DeleteMapping("/{dictIds}")
    public R remove(@PathVariable String[] dictIds) {
        return toAjax(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * wipe cache
     */
    @DeleteMapping("/clearCache")
    public R clearCache() {
        dictTypeService.clearCache();
        return R.success();
    }

    /**
     * Get dictionary selection box list
     */
    @GetMapping("/optionselect")
    public R optionselect() {
        List<KoDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return R.success(dictTypes);
    }
}
