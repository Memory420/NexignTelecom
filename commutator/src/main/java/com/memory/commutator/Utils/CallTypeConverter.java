package com.memory.commutator.Utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CallTypeConverter implements AttributeConverter<CallType, String> {

    @Override
    public String convertToDatabaseColumn(CallType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CallType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CallType.fromCode(dbData);
    }
}

