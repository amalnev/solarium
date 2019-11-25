package ru.amalnev.solarium.library.strings;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("replace")
@FunctionArguments({"sourceString", "pattern", "replacement"})
public class Replace extends AbstractNativeFunction {
    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String sourceString = getScalarArgument(context, "sourceString");
        final String pattern = getScalarArgument(context, "pattern");
        final String replacement = getScalarArgument(context, "replacement");

        setReturnValue(context, sourceString.replaceAll(pattern, replacement));
    }
}
