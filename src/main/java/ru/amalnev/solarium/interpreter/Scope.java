package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope implements IVariableScope
{
    private Map<String, Object> lvalues = new HashMap<>();

    @Setter
    @Getter
    private Scope parentScope;

    @Override
    public void defineScalar(String name)
    {
        if(lvalues.containsKey(name)) throw new RedefinitionException(name);
        lvalues.put(name, null);
    }

    @Override
    public void defineArray(String name)
    {
        if(lvalues.containsKey(name)) throw new RedefinitionException(name);
        lvalues.put(name, new ArrayList<>());
    }

    @Override
    public Object getValue(String name)
    {
        if(!lvalues.containsKey(name) && parentScope == null) throw new VariableNotDefinedException(name);
        if(!lvalues.containsKey(name)) return parentScope.getValue(name);
        return lvalues.get(name);
    }

    @Override
    public Object getValue(String name, Integer index)
    {
        final List<Object> array = (List<Object>) getValue(name);
        return array.get(index);
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass)
    {
        return (T) getValue(name);
    }

    @Override
    public <T> T getValue(String name, Integer index, Class<T> valueClass)
    {
        return (T) getValue(name, index);
    }

    @Override
    public void setValue(String name, Object value)
    {
        if(lvalues.containsKey(name))
        {
            lvalues.put(name, value);
            return;
        }

        if(parentScope == null) throw new VariableNotDefinedException(name);
        parentScope.setValue(name, value);
    }

    @Override
    public void setValue(String name, Integer index, Object value)
    {
        final List<Object> array = (List<Object>) getValue(name);
        array.set(index, value);
    }
}
