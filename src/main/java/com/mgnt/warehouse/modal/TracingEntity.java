package com.mgnt.warehouse.modal;

import com.mgnt.warehouse.utils.Action;
import com.mgnt.warehouse.utils.TraceItem;
import com.mgnt.warehouse.validate.EnumValidator;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TracingEntity extends BaseEntity {

    @EnumValidator(enumClazz = Action.class)
    private String action;

    @EnumValidator(enumClazz = TraceItem.class)
    private String traceItem;

    private String details;

}
