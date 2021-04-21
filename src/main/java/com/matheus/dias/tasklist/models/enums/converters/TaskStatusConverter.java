package com.matheus.dias.tasklist.models.enums.converters;

import com.matheus.dias.tasklist.models.enums.TaskStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.nonNull;

@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        return nonNull(attribute) ? attribute.toValue() : null;
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer column) {
        return nonNull(column) ? TaskStatus.fromValue(column) : null;
    }

}
