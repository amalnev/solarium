package ru.amalnev.solarium.library.ip;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("isReachable")
@FunctionArguments("ipAddress")
public class IsReachable extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String ipAddress = getScalarArgument(context, "ipAddress");
            final String pingCommand = System.getProperty("os.name").contains("Windows") ?
                    "ping -n 1 " + ipAddress :
                    "ping -c 1 " + ipAddress;
            final Process p1 = java.lang.Runtime.getRuntime().exec(pingCommand);
            final int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            setReturnValue(context, reachable);
        }
        catch (IOException | InterruptedException e)
        {
            throw new InterpreterException(e);
        }
    }
}
