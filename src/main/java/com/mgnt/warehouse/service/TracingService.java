package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.TracingEntity;
import com.mgnt.warehouse.repository.TracingRepository;
import com.mgnt.warehouse.modal.enums.Action;
import com.mgnt.warehouse.modal.enums.TraceItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TracingService {
    private final TracingRepository tracingRepository;

    public void save(Action action, TraceItem item, String details) {
        tracingRepository.save(new TracingEntity(action.name(), item.name(), details));
    }

    public List<TracingEntity> getHistory(String traceItem) {
        return tracingRepository.findHistoryLast30sDay(traceItem.toUpperCase());
    }

}
