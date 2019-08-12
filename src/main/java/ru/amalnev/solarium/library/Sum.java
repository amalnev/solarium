package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("sum")
@FunctionArguments({"a", "b"})
public class Sum extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context)
    {
        final Integer a = context.getLocalVariableValue("a", Integer.class);
        final Integer b = context.getLocalVariableValue("b", Integer.class);
        final Integer result = a + b;
        setReturnValue(context, result);
    }
}
