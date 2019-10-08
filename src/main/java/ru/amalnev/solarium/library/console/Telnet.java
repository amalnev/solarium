package ru.amalnev.solarium.library.console;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("telnet")
@FunctionArguments("host")
public class Telnet extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String host = getScalarArgument(context, "host");
            final TelnetConnection telnetConnection = new TelnetConnection(host);
            setReturnValue(context, telnetConnection);
        }
        catch (IOException e)
        {
            setReturnValue(context, null);
        }
    }
}
