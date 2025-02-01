package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.TracingEntity;
import com.mgnt.warehouse.service.TracingService;
import com.mgnt.warehouse.utils.TraceItem;
import com.mgnt.warehouse.validate.EnumValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryController {
    private final TracingService tracingService;

    @GetMapping("{item}")
    public List<TracingEntity> getHistory(@PathVariable("item") @Valid @EnumValidator(enumClazz = TraceItem.class) String item) {
        return tracingService.getHistory(item);
    }
}
