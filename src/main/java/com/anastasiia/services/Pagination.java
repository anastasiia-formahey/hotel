package com.anastasiia.services;

import javax.servlet.http.HttpServletRequest;

/**
 * Pagination class implements pagination to use it on views
 */
public class Pagination {
    public static final int RECORDS_PER_PAGE = 6;

    /**
     * Method sets pagination parameters into request to use it on java servlet pages
     * @param request HttpServletRequest request
     */
    public static void setPagination(HttpServletRequest request) {
        int currentPage = getCurrentPage(request.getParameter("currentPage"));
        int rows = (int) request.getSession().getAttribute("rows");
        String orderBy = request.getParameter("orderBy");
        if (orderBy == null){
            orderBy="id";
        }
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0 && rows % RECORDS_PER_PAGE != 0) {
            nOfPages += 1;
        }
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("orderBy", orderBy);
    }

    /**
     * @param currentPageParam value of current page
     * @return int value of current page
     */
    public static int getInt(String currentPageParam) {
        int current;
        try {
            current = Integer.parseInt(currentPageParam);
        } catch (NumberFormatException e) {
            current = -1;
        }
        return current;
    }

    /**
     *
     * @param currentPageParam value of current page
     * @return int value of current page
     */
    public static int getCurrentPage(String currentPageParam) {
        return (currentPageParam == null || getInt(currentPageParam) <= 1) ? 1 : getInt(currentPageParam);
    }
}