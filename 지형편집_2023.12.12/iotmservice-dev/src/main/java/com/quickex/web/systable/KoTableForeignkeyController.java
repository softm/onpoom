package com.quickex.web.systable;


import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTableForeignkey;
import com.quickex.service.systable.IKoTableForeignkeyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * table-foreignkey - Controller
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/api/ko-table-foreignkey")
public class KoTableForeignkeyController {

    @Resource
    private IKoTableForeignkeyService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTableForeignkey entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoTableForeignkey entity) {
        return service.delete(entity);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoTableForeignkey entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoTableForeignkey entity) {
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoTableForeignkey entity) {
        return service.list(entity);
    }

}

