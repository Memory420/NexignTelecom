package com.memory.brt.Util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CallTypeConverter
        implements AttributeConverter<CallType,String> {

    @Override
    public String convertToDatabaseColumn(CallType attribute) {
        return attribute.getCode();
    }

    @Override
    public CallType convertToEntityAttribute(String dbData) {
        return CallType.fromCode(dbData);
    }
}
