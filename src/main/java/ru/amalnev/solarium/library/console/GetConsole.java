package ru.amalnev.solarium.library.console;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("getConsole")
@FunctionArguments("serialInterface")
public class GetConsole extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final ISerialInterface serialInterface = getScalarArgument(context, "serialInterface");
            setReturnValue(context, serialInterface.getConsole());
        }
        catch (IOException e)
        {
            setReturnValue(context, null);
        }
    }
}
