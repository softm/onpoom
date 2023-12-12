package com.quickex.web.systable;


import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTableIndexes;
import com.quickex.service.systable.IKoTableIndexesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  ko-table-indexes - Controller
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/api/ko-table-indexes")
public class KoTableIndexesController {

    @Resource
    private IKoTableIndexesService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTableIndexes entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoTableIndexes entity) {
        return service.delete(entity);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoTableIndexes entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoTableIndexes entity) {
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoTableIndexes entity) {
        return service.list(entity);
    }

}

