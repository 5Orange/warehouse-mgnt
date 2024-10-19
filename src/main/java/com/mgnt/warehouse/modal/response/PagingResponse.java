package com.mgnt.warehouse.modal.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagingResponse<T> {
    private List<T> data;
    private int pageSize;
    private Long totalItems;
    private boolean hasNext;
    private int totalPage;

    public PagingResponse(Page<T> pageData) {
        this.data = pageData.getContent();
        this.totalPage = pageData.getTotalPages();
        this.totalItems = pageData.getTotalElements();
        this.hasNext = pageData.hasNext();
    }

}
