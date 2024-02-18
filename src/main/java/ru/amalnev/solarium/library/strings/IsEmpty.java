package ru.amalnev.solarium.library.strings;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("isEmpty")
@FunctionArguments({"string"})
public class IsEmpty extends AbstractNativeFunction {

    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String str = getScalarArgument(context, "string");
        setReturnValue(context, str == null || str.trim().isEmpty());
    }
}
