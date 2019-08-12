package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("print")
@FunctionArguments("what")
public class Print extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context)
    {
        final Object what = context.getLocalVariableValue("what", Object.class);
        System.out.println(what.toString());
    }
}
