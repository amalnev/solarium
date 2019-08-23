package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

public abstract class AbstractNativeFunction extends FunctionDefinition implements INativeFunction
{
    protected void setReturnValue(final ExecutionContext context, final Object returnValue)
    {
        context.setReturnValue(returnValue);
    }
}
