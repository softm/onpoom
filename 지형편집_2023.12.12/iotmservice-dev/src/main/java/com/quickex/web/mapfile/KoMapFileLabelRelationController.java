package com.quickex.web.mapfile;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationUpdateDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileLabelRelation;
import com.quickex.service.mapfile.IKoMapFileLabelRelationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * Labels and model files are associated with front-end controllers
 * </p>
 *
 * @author ffzh
 * @since 2021-12-27
 */
@RestController
@RequestMapping("/api/ko-map-file-label-relation")
public class KoMapFileLabelRelationController  extends BaseController {

    @Resource
    private IKoMapFileLabelRelationService service;

    @PostMapping("/list")
    public R list(@RequestBody KoMapFile entity) {
        return service.list(entity);
    }

    @PostMapping("/add")
    public R add(@RequestBody KoMapFileLabelRelation entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoMapFileLabelRelation entity) {
        return service.delete(entity);
    }

    /**
     * All labels for file binding
     * @param entity
     * @return
     */
    @PostMapping("/listall")
    public R listall(@RequestBody KoMapFile entity) {
        return service.listall(entity);
    }

    /**
     * update All labels for file binding
     * @param entity
     * @return
     */
    @PostMapping("/updateall")
    public R updateall(@RequestBody KoMapFileLabelRelationUpdateDto par) {
        return service.updateall(par);
    }

}

