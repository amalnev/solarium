package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("startsWith")
@FunctionArguments({"sourceString", "substring"})
public class StartsWith extends AbstractNativeFunction {
    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String sourceString = getScalarArgument(context, "sourceString");
        final String substring = getScalarArgument(context, "substring");
        setReturnValue(context, sourceString.startsWith(substring));
    }
}
