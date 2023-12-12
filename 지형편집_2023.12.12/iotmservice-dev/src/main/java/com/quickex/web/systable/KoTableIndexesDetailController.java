package com.quickex.web.systable;


import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTableIndexesDetail;
import com.quickex.service.systable.IKoTableIndexesDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * table-indexes-detail - Controller
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/api/ko-table-indexes-detail")
public class KoTableIndexesDetailController {

    @Resource
    private IKoTableIndexesDetailService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTableIndexesDetail entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoTableIndexesDetail entity) {
        return service.delete(entity);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoTableIndexesDetail entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoTableIndexesDetail entity) {
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoTableIndexesDetail entity) {
        return service.list(entity);
    }

}

