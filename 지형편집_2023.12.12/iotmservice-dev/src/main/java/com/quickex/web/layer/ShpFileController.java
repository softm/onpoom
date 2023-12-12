package com.quickex.web.layer;


import com.quickex.core.annotation.Log;
import com.quickex.core.controller.BaseController;
import com.quickex.core.enums.BusinessType;
import com.quickex.core.page.TableDataInfo;
import com.quickex.core.result.R;
import com.quickex.core.util.poi.ExcelUtil;
import com.quickex.domain.layer.KoShpFile;
import com.quickex.service.layer.IKoShpFileService;
import com.quickex.service.layer.IUpLoadService;
import lombok.extern.slf4j.Slf4j;
//import org.opengis.referencing.FactoryException;
//import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/shpfile")
public class ShpFileController extends BaseController
{
//    @Autowired
//    private IKoShpFileService egovFileService;
//
//    @Resource
//    private IUpLoadService iUpLoadService;
//
//    @PostMapping("/test")
//    public R listaaaaa(@RequestParam(value = "file",required = false) String file)
//    {
//            return R.success(file);
//    }
//    @PostMapping("/test1")
//    public R lis1t(@RequestParam(value = "filesssss",required = false) String file)
//    {
//        return R.success(file);
//    }
//
//    @PostMapping("/test2")
//    public R xxxxxxx(HttpServletRequest request, HttpServletResponse response)
//    {
//        Map<String,String> parmMap=new HashMap<String,String>();
//        //Method 1: getparametermap() to obtain the request parameter map
//        Map<String,String[]> map= request.getParameterMap();
//        //Parameter name
//        Set<String> key=map.keySet();
//        //Parameter iterator
//        Iterator<String> iterator = key.iterator();
//        while(iterator.hasNext()){
//            String k=iterator.next();
//            parmMap.put(k, map.get(k)[0]);
//        }
//        System.out.println("parmMap====="+parmMap.toString());
//        return R.success();
////        String xxx = request.getAttribute("xxx").toString();
////        return R.success(xxx);
//    }
//
//    /**
//     * Upload the zip and parse the SHP data in the zip
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    //@PostMapping(value = "/uploadFile", headers = "content-type=multipart/*")
//    @PostMapping(value = "/uploadFile")
//    //public R importSqlLite(@RequestParam("file") MultipartFile file) throws IOException {
//        public R importSqlLite(@RequestParam("file") String file) throws IOException {
//        return R.success();
////            R r = null;
////        try {
////
////            r = iUpLoadService.upLoadZip(file);
////            return r;
////        } catch (IOException e) {
////            e.printStackTrace();
////            return R.error();
////        } catch (FactoryException e) {
////            e.printStackTrace();
////            return R.error();
////        } catch (TransformException e) {
////            e.printStackTrace();
////            return R.error();
////        }
////        AjaxResult ajaxResult = iUpLoadService.readShp();
//    }
//
//

//    @GetMapping("/list")
//    public TableDataInfo list(KoShpFile egovFile)
//    {
//        startPage();
//        List<KoShpFile> list = egovFileService.selectEgovFileList(egovFile);
//        return getDataTable(list);
//    }
//

////    @PreAuthorize("@ss.hasPermi('file:file:export')")
//    @Log(title = "upFile", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public R export(KoShpFile egovFile)
//    {
//        List<KoShpFile> list = egovFileService.selectEgovFileList(egovFile);
//        ExcelUtil<KoShpFile> util = new ExcelUtil<KoShpFile>(KoShpFile.class);
////        return util.exportExcel(list, "upFile data");
//        return R.success();
//    }
//

//    @GetMapping(value = "/getInfo/{fileId}")
//    public R getInfo(@PathVariable("fileId") String fileId)
//    {
//        return R.success(egovFileService.selectEgovFileByFileId(fileId));
//    }
//

////    @PreAuthorize("@ss.hasPermi('file:file:add')")
//    @Log(title = "upFile", businessType = BusinessType.INSERT)
//    @PostMapping
//    public R add(@RequestBody KoShpFile egovFile)
//    {
//        return toAjax(egovFileService.insertEgovFile(egovFile));
//    }
//

////    @PreAuthorize("@ss.hasPermi('file:file:edit')")
//    @Log(title = "upFile", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public R edit(@RequestBody KoShpFile egovFile)
//    {
//        return toAjax(egovFileService.updateEgovFile(egovFile));
//    }
//

////    @PreAuthorize("@ss.hasPermi('file:file:remove')")
//    @Log(title = "upFile", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{fileIds}")
//    public R remove(@PathVariable String[] fileIds)
//    {
//        return toAjax(egovFileService.deleteEgovFileByFileIds(fileIds));
//    }
}
