package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("createArray")
public class CreateArray extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        context.setReturnValue(new Value().convertToArray());
    }
}
