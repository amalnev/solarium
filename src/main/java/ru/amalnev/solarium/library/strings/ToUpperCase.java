package ru.amalnev.solarium.library.strings;


import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("toUpperCase")
@FunctionArguments("what")
public class ToUpperCase extends AbstractNativeFunction {
    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String what = getScalarArgument(context, "what");
        setReturnValue(context, what.toUpperCase());
    }
}
