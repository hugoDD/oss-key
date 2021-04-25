package org.maxkey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PageResults<T> {
    private final Logger _logger = LoggerFactory.getLogger(PageResults.class);
    private int page = 0;
    private int total = 0;
    private int totalPage = 0;
    private Long records = 0L;
    private List<T> rows;

    public PageResults() {
        this._logger.debug("Grid.");
    }

    public PageResults(int currentPage, int pageResults, Long recordsCount) {
        this.pageCount(currentPage, pageResults, recordsCount);
        this._logger.debug("Grid page : " + this.page + " , records : " + this.records + " , total : " + this.total);
    }


    public PageResults(int currentPage, int pageResults, Long recordsCount, List<T> rows) {
        this.pageCount(currentPage, pageResults, recordsCount);
        this.rows = rows;
    }

    public PageResults(int currentPage, int pageResults, Integer recordsCount, List<T> rows) {
        this.pageCount(currentPage, pageResults, recordsCount);
        this.rows = rows;
    }

    public PageResults(int currentPage, int pageResults, int totalPage, Long recordsCount, List<T> rows) {
        this.pageCount(currentPage, pageResults, recordsCount);
        this.rows = rows;
        this.totalPage = totalPage;
    }

    public PageResults(int currentPage, int pageResults, int totalPage, Integer recordsCount, List<T> rows) {
        this.pageCount(currentPage, pageResults, recordsCount);
        this.rows = rows;
        this.totalPage = totalPage;
    }

    public void pageCount(int currentPage, int pageResults, Long recordsCount) {
        this.page = currentPage;
        this.total = (int)(recordsCount % (long)pageResults > 0L ? recordsCount / (long)pageResults + 1L : recordsCount / (long)pageResults);
        this.records = recordsCount;
    }

    public void pageCount(int currentPage, int pageResults, Integer recordsCount) {
        this.page = currentPage;
        this.total = recordsCount % pageResults > 0 ? recordsCount / pageResults + 1 : recordsCount / pageResults;
        this.records = Long.parseLong("" + recordsCount);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
