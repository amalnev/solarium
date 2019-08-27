package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;

import java.util.List;

@FunctionName("size")
@FunctionArguments("array")
public class Size extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final List array = context.getValue("array", List.class);
        setReturnValue(context, array.size());
    }
}
