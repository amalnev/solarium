package ru.amalnev.solarium.library.strings;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.util.Optional;

@FunctionName("trim")
@FunctionArguments({"string"})
public class Trim extends AbstractNativeFunction {

    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String str = getScalarArgument(context, "string");
        setReturnValue(context, Optional.ofNullable(str).map(String::trim).orElse(null));
    }
}
