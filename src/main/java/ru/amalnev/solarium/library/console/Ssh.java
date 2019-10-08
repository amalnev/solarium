package ru.amalnev.solarium.library.console;

import com.jcraft.jsch.JSchException;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("ssh")
@FunctionArguments({"host", "username", "password"})
public class Ssh extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String host = getScalarArgument(context, "host");
            final String username = getScalarArgument(context, "username");
            final String password = getScalarArgument(context, "password");

            setReturnValue(context, new SshConnection(host, username, password));
        }
        catch (JSchException e)
        {
            setReturnValue(context, null);
        }
    }
}
