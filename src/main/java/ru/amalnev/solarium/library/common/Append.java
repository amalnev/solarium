package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("append")
@FunctionArguments({"array", "element"})
public class Append extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final IValue array = context.findVariable("array");
        final IValue element = context.findVariable("element");
        if (array.isScalar()) array.convertToArray();
        array.addArrayElement(element);
        context.setReturnValue(array);
    }
}
