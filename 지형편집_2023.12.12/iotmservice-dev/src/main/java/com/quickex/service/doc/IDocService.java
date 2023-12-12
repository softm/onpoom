package com.quickex.service.doc;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import org.springframework.web.multipart.MultipartFile;

public interface IDocService {

    R download1(JSONObject object);

    R download2(JSONObject object);

    R download3(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    );

    R download4(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    );

    R download5(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    );
    R download6(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42,

            MultipartFile file51,
            MultipartFile file52,
            MultipartFile file61,
            MultipartFile file62,
            MultipartFile file71,
            MultipartFile file72,
            MultipartFile file81,
            MultipartFile file82
    );

    R download7(MultipartFile file,String position,String height,String distance,String createUser);

    R epsgConvertUpload(MultipartFile file);

    R epsgConvertDownload(JSONObject entity);

    R download8(JSONObject object);

    R download9(String files,JSONObject entity);


    R download10(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42,
            String startPosition,
            String visualHeight,
            String visibleDistance,
            String horizontalViewingAngle,
            String verticalViewingAngle,
            String createUser
    );


    R download11(JSONObject object);

    R downloadExampleOfOccupationLicenseLedger(JSONObject data);

    R downloadExampleOfOccupationLicenseLedger1(JSONObject data);

    R readRoadOccupationPermitAccountTemplate(MultipartFile file);
    R downloadRoadOccupationPermitAccount(JSONObject data);
    R downloadRoadOccupationPermitAccountTemplate();

    R download12(JSONObject data);
}
