package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("sum")
@FunctionArguments({"a", "b"})
public class Sum extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context) throws InterpreterException
    {
        final Integer a = getScalarArgument(context, "a");
        final Integer b = getScalarArgument(context, "b");
        final Integer result = a + b;
        setReturnValue(context, result);
    }
}
