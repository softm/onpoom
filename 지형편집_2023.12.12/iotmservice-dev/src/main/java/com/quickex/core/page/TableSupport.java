package com.quickex.core.page;


import com.quickex.core.util.ServletUtils;

/**
 * Table data processing
 *
 * @author
 */
public class TableSupport {
    /**
     * Current record start index
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Number of records per page
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * Sort column
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * The sorting direction is "desc" or "ASC"
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Encapsulating paging objects
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        if(pageDomain.getPageNum()==null){
            pageDomain.setPageNum(1);
        }
        if(pageDomain.getPageSize()==null){
            pageDomain.setPageSize(10);
        }
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
