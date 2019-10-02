package ru.amalnev.solarium.interpreter.memory;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.VariableNotDefinedException;

import java.util.HashMap;
import java.util.Map;

public class VariableScope implements IVariableScope
{
    private Map<String, IValue> variables = new HashMap<>();

    @Getter
    @Setter
    private VariableScope parentScope;

    @Override
    public IValue defineVariable(String name)
    {
        final Value value = new Value();
        variables.put(name, value);
        return value;
    }

    @Override
    public IValue findVariable(String name) throws InterpreterException
    {
        if (!variables.containsKey(name) && parentScope == null) throw new VariableNotDefinedException(name);
        if (!variables.containsKey(name)) return parentScope.findVariable(name);
        return variables.get(name);
    }
}
