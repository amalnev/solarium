package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class LocalVariable
{
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;

    private Map<String, Object> fields = new HashMap<>();

    public LocalVariable(final String name)
    {
        this.name = name;
    }

    public Object getFieldValue(String fieldName)
    {
        return fields.get(fieldName);
    }

    public void setFieldValue(String fieldName, Object fieldValue)
    {
        fields.put(fieldName, fieldValue);
    }
}
