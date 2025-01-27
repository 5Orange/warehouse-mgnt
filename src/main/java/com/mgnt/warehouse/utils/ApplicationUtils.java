package com.mgnt.warehouse.utils;

import com.mgnt.warehouse.modal.common.MetricSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class ApplicationUtils {

    public static Pageable getPageable(MetricSearch metricSearch) {

        if (StringUtils.isEmpty(metricSearch.getSortField())) {
            return PageRequest.of(metricSearch.getPageNumber(), metricSearch.getPageSize());
        } else {
            return PageRequest.of(metricSearch.getPageNumber(), metricSearch.getPageSize(),
                    metricSearch.getDesc() == null ? Direction.ASC : Direction.DESC,
                    metricSearch.getSortField());
        }
    }

}
