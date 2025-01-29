package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.TracingEntity;
import com.mgnt.warehouse.repository.TracingRepository;
import com.mgnt.warehouse.utils.Action;
import com.mgnt.warehouse.utils.TraceItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TracingService {
    private final TracingRepository tracingRepository;

    public void save(Action action, TraceItem item, String details) {
        tracingRepository.save(new TracingEntity(action.name(), item.name(), details));
    }

}
