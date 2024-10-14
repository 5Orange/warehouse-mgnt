package com.mgnt.warehouse.modal.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingResponse<T> {
    private List<T> data;
    private int pageSize;
    private Long totalItems;
    private boolean hasNext;
    private int totalPage;

}
