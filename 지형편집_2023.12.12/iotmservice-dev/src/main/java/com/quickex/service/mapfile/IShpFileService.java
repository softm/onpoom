package com.quickex.service.mapfile;

import com.quickex.core.result.R;

public interface IShpFileService {
    R geojsonToShp(String body);
}
