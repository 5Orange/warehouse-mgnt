package com.mgnt.warehouse.modal.common;

import java.util.List;

import lombok.Data;

@Data
public class MetricSearch {
    private String sortField;
    private Boolean desc;

    private Integer pageSize;
    private Integer pageNumber;

    private List<MetricFilter> metricFilters;

    public Integer getPageSize() {
        return this.pageSize == null || this.pageSize.equals(0) ? 10 : this.pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber == null ? 0 : this.pageNumber;
    }

}
