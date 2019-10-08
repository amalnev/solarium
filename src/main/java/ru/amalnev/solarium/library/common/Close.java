package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.Closeable;
import java.io.IOException;

@FunctionName("close")
@FunctionArguments({"what"})
public class Close extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final Closeable what = getScalarArgument(context, "what");
        try
        {
            what.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
