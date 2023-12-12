package com.quickex.core.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.quickex.core.page.PageDomain;
import com.quickex.core.page.TableDataInfo;
import com.quickex.core.page.TableSupport;
import com.quickex.core.result.R;
import com.quickex.core.util.DateUtils;
import com.quickex.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

@Slf4j
public class BaseController {



    /**
     * Automatically convert the date format string passed from the foreground to date type
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date Type conversion
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }


    /**
     * Set request paging data
     */
    protected PageDomain startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        return pageDomain;
    }

    /**
     * Response request paging data
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("query was successful");
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    /**
     * Response return result
     *
     * @param rows Number of affected rows
     * @return Operation results
     */
    protected R toAjax(int rows) {
        return rows > 0 ? R.success() : R.error();
    }

    /**
     * Response return result
     *
     * @param b Return results
     * @return
     */
    protected R toAjax(boolean b) {
        return b ==true ? R.success() : R.error();
    }

    /**
     * Page Jump
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }
}
