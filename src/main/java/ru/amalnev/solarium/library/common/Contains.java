package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("contains")
@FunctionArguments({"container", "element"})
public class Contains extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final IValue container = context.findVariable("container");
        if (container.isScalar() && container.getScalarValue().getClass().equals(String.class))
        {
            final String element = getScalarArgument(context, "element");
            setReturnValue(context, ((String) container.getScalarValue()).contains(element));
        }
        else
        {
            final IValue element = context.findVariable("element");
            for (int i = 0; i < container.getArraySize(); i++)
            {
                if (element.equals(container.getArrayElement(i)))
                {
                    setReturnValue(context, true);
                    return;
                }
            }

            setReturnValue(context, false);
        }
    }
}
