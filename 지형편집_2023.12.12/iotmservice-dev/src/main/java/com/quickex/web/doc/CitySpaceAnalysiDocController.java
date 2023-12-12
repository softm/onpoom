package com.quickex.web.doc;

import com.alibaba.fastjson.JSON;
import com.quickex.core.result.R;
import com.quickex.service.doc.ICitySpaceAnalysiDoc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/CitySpaceAnalysiDoc")
public class CitySpaceAnalysiDocController {

    @Resource
    private ICitySpaceAnalysiDoc service;

    @PostMapping("/doc1")
    public R doc1(@RequestBody String json){
        return service.doc1(JSON.parseObject(json));
    }

    @PostMapping("/doc2")
    public R doc2(@RequestBody String json){
        return service.doc2(JSON.parseObject(json));
    }

    @PostMapping("/doc2_1")
    public R doc2_1(@RequestBody String json){
        return service.doc2_1(JSON.parseObject(json));
    }

    @PostMapping("/doc3")
    public R doc3(@RequestBody String json){
        return service.doc3(JSON.parseObject(json));
    }

    @PostMapping("/doc4")
    public R doc4(@RequestBody String json){
        return service.doc4(JSON.parseObject(json));
    }

    @PostMapping("/doc5")
    public R doc5(@RequestBody String json){
        return service.doc5(JSON.parseObject(json));
    }

    @PostMapping("/doc6")
    public R doc6(@RequestBody String json){
        return service.doc6(JSON.parseObject(json));
    }

    @PostMapping("/doc7")
    public R doc7(@RequestBody String json){
        return service.doc7(JSON.parseObject(json));
    }

    @PostMapping("/doc8")
    public R doc8(@RequestBody String json){
        return service.doc8(JSON.parseObject(json));
    }

    @PostMapping("/doc9")
    public R doc9(@RequestBody String json){
        return service.doc9(JSON.parseObject(json));
    }

    @PostMapping("/doc10")
    public R doc10(@RequestBody String json){
        return service.doc10(JSON.parseObject(json));
    }

    @PostMapping("/doc11")
    public R doc11(@RequestBody String json){
        return service.doc11(JSON.parseObject(json));
    }

    @PostMapping("/doc12")
    public R doc12(@RequestBody String json){
        return service.doc12(JSON.parseObject(json));
    }

    @PostMapping("/doc13")
    public R doc13(@RequestBody String json){
        return service.doc13(JSON.parseObject(json));
    }

    @PostMapping("/doc14")
    public R doc14(@RequestBody String json){
        return service.doc14(JSON.parseObject(json));
    }


    @PostMapping("/doc15")
    public R doc15(@RequestBody String json){
        return service.doc15(JSON.parseObject(json));
    }
    @PostMapping("/doc16")
    public R doc16(@RequestBody String json){
        return service.doc16(JSON.parseObject(json));
    }


    @PostMapping("/doc17")
    public R doc17(@RequestBody String json){
        return service.doc17(JSON.parseObject(json));
    }
    @PostMapping("/doc18")
    public R doc18(@RequestBody String json){
        return service.doc18(JSON.parseObject(json));
    }
    @PostMapping("/doc19")
    public R doc19(@RequestBody String json){
        return service.doc19(JSON.parseObject(json));
    }
    @PostMapping("/doc20")
    public R doc20(@RequestBody String json){
        return service.doc20(JSON.parseObject(json));
    }




    @PostMapping("/doc21")
    public R doc21(@RequestBody String json){
        return service.doc21(JSON.parseObject(json));
    }


    @PostMapping("/doc22")
    public R doc22(@RequestBody String json){
        return service.doc22(JSON.parseObject(json));
    }

    @PostMapping("/doc23")
    public R doc23(@RequestBody String json){
        return service.doc23(JSON.parseObject(json));
    }

    @PostMapping("/doc24")
    public R doc24(@RequestBody String json){
        return service.doc24(JSON.parseObject(json));
    }


    @PostMapping("/doc25")
    public R doc25(@RequestBody String json){
        return service.doc25(JSON.parseObject(json));
    }



    @PostMapping("/doc26")
    public R doc26(@RequestBody String json){
        return service.doc26(JSON.parseObject(json));
    }

    @PostMapping("/doc27")
    public R doc27(@RequestBody String json){
        return service.doc27(JSON.parseObject(json));
    }





    @PostMapping("/doc28")
    public R doc28(@RequestBody String json){
        return service.doc28(JSON.parseObject(json));
    }

    @PostMapping("/doc29")
    public R doc29(@RequestBody String json){
        return service.doc29(JSON.parseObject(json));
    }
    @PostMapping("/doc30")
    public R doc30(@RequestBody String json){
        return service.doc30(JSON.parseObject(json));
    }


    @PostMapping("/doc31")
    public R doc31(@RequestBody String json){
        return service.doc31(JSON.parseObject(json));
    }

     @PostMapping("/doc32")
    public R doc32(@RequestBody String json){
        return service.doc32(JSON.parseObject(json));
    }

    @PostMapping("/doc33")
    public R doc33(@RequestBody String json){
        return service.doc33(JSON.parseObject(json));
    }

    @PostMapping("/doc34")
    public R doc34(@RequestBody String json){
        return service.doc34(JSON.parseObject(json));
    }


    @PostMapping("/doc35")
    public R doc35(@RequestBody String json){
        return service.doc35(JSON.parseObject(json));
    }

    @PostMapping("/doc36")
    public R doc36(@RequestBody String json){
        return service.doc36(JSON.parseObject(json));
    }


    @PostMapping("/doc37")
    public R doc37(@RequestBody String json){
        return service.doc37(JSON.parseObject(json));
    }

    @PostMapping("/doc38")
    public R doc38(@RequestBody String json){
        return service.doc38(JSON.parseObject(json));
    }


    @PostMapping("/doc39")
    public R doc39(@RequestBody String json){
        return service.doc39(JSON.parseObject(json));
    }

    @PostMapping("/doc40")
    public R doc40(@RequestBody String json){
        return service.doc40(JSON.parseObject(json));
    }


    @PostMapping("/doc41")
    public R doc41(@RequestBody String json){
        return service.doc41(JSON.parseObject(json));
    }
    @PostMapping("/doc42")
    public R doc42(@RequestBody String json){
        return service.doc42(JSON.parseObject(json));
    }
    @PostMapping("/doc43")
    public R doc43(@RequestBody String json){
        return service.doc43(JSON.parseObject(json));
    }


    @PostMapping("/doc44")
    public R doc44(@RequestBody String json){
        return service.doc44(JSON.parseObject(json));
    }


    @PostMapping("/doc45")
    public R doc45(@RequestBody String json){
        return service.doc45(JSON.parseObject(json));
    }
}
