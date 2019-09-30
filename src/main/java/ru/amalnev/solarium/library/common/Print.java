package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("print")
@FunctionArguments("what")
public class Print extends AbstractNativeFunction
{
    private void print(IValue value) throws InterpreterException
    {
        if (value.isScalar())
        {
            final Object scalarValue = value.getScalarValue();
            if (scalarValue == null)
            {
                System.out.print("null");
            }
            else
            {
                System.out.print(scalarValue.toString());
            }
        }
        else
        {
            System.out.print("[");
            for (int i = 0; i < value.getArraySize(); i++)
            {
                print(value.getArrayElement(i));
                if (i != value.getArraySize() - 1)
                    System.out.print(", ");
            }
            System.out.print("]");
        }
    }

    @Override
    public void call(final ExecutionContext context) throws InterpreterException
    {
        final IValue what = context.findVariable("what");
        print(what);
    }
}
