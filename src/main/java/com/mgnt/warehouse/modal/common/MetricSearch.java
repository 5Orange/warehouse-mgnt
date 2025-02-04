package com.mgnt.warehouse.modal.common;

import java.util.List;

public record MetricSearch(String sortField, Boolean desc, Integer pageSize, Integer pageNumber,
                           List<MetricFilter> metricFilters) {

    public Integer getPageSize() {
        return this.pageSize == null || this.pageSize.equals(0) ? 10 : this.pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber == null ? 0 : this.pageNumber;
    }

}
