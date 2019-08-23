package ru.amalnev.solarium.interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LValue implements ILValue
{
    private String variableName;

    private Integer index;

    private IVariableScope variableScope;

    public LValue(String variableName, IVariableScope variableScope)
    {
        this.variableScope = variableScope;
        this.variableName = variableName;
    }

    @Override
    public void setValue(Object value)
    {
        if(index != null) variableScope.setValue(variableName, index, value);
        else variableScope.setValue(variableName, value);
    }

    @Override
    public Object getValue()
    {
        if(index != null) return variableScope.getValue(variableName, index);
        return variableScope.getValue(variableName);
    }
}
