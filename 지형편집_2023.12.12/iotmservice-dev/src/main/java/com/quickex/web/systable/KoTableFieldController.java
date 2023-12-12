package com.quickex.web.systable;


import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTableField;
import com.quickex.service.systable.IKoTableFieldService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  table-field - Controller
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/api/ko-table-field")
public class KoTableFieldController {

    @Resource
    private IKoTableFieldService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTableField entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoTableField entity) {
        return service.delete(entity);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoTableField entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoTableField entity) {
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoTableField entity) {
        return service.list(entity);
    }

}

