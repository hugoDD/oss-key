package org.apache.mybatis.jpa.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;

public class JpaPagination {
    @JsonIgnore
    @Transient
    protected int rows;
    @JsonIgnore
    @Transient
    protected int pageSize = 20;
    @JsonIgnore
    @Transient
    protected int pageNumber = 1;
    @JsonIgnore
    @Transient
    protected int startRow;
    @JsonIgnore
    @Transient
    protected int endRow;
    @JsonIgnore
    @Transient
    protected String sidx;
    @JsonIgnore
    @Transient
    protected String sortOrder;
    @JsonIgnore
    @Transient
    protected String sortKey;
    @JsonIgnore
    @Transient
    protected String orderBy;
    @JsonIgnore
    @Transient
    protected boolean pageable = false;
    @JsonIgnore
    @Transient
    protected String pageResultSelectUUID;

    public JpaPagination() {
    }

    @JsonIgnore
    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.pageSize = rows;
        this.calculate();
    }

    @JsonIgnore
    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        this.calculate();
    }

    @JsonIgnore
    public String getSidx() {
        return this.sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
        this.setSortKey();
    }

    @JsonIgnore
    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        this.setSortKey();
    }

    @JsonIgnore
    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    @JsonIgnore
    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public void calculate() {
        if (this.pageNumber >= 1 && this.pageSize > 0) {
            this.startRow = (this.pageNumber - 1) * this.pageSize;
            this.endRow = this.startRow + this.pageSize;
        }

    }

    @JsonIgnore
    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonIgnore
    public String getSortKey() {
        return this.sortKey;
    }

    public void setSortKey() {
        if (this.sidx != null && this.sortOrder != null && !this.sidx.equals("") && !this.sortOrder.equals("")) {
            this.sortKey = " " + this.sidx + " " + this.sortOrder + " ";
            this.setOrderBy();
        }

    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    @JsonIgnore
    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy() {
        if (this.sortKey != null && !this.sortKey.equals("")) {
            this.orderBy = " ORDER BY  " + this.sidx + " " + this.sortOrder + " ";
        }

    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isPageable() {
        return this.pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public void setPageable() {
        this.setPageable(true);
    }

    public void setPageResultSelectUUID(String pageResultSelectUUID) {
        this.pageResultSelectUUID = pageResultSelectUUID;
    }

    public String getPageResultSelectUUID() {
        return this.pageResultSelectUUID;
    }

    public String toString() {
        return "Pagination [rows=" + this.rows + ", pageResults=" + this.pageSize + ", page=" + this.pageNumber + ", startRow=" + this.startRow + ", endRow=" + this.endRow + ", sidx=" + this.sidx + ", sord=" + this.sortOrder + ", sortKey=" + this.sortKey + ", orderBy=" + this.orderBy + ", pageable=" + this.pageable + "]";
    }
}
