package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

public abstract class AbstractNativeFunction extends FunctionDefinition implements INativeFunction
{
    protected void setReturnValue(final ExecutionContext context, final Object returnValue)
    {
        context.setReturnValue(new Value(returnValue));
    }

    protected void setReturnValue(final ExecutionContext context, final IValue returnValue) {
        context.setReturnValue(returnValue);
    }

    protected <T> T getScalarArgument(ExecutionContext context, String argumentName) throws InterpreterException
    {
        return (T) context.findVariable(argumentName).getScalarValue();
    }
}
