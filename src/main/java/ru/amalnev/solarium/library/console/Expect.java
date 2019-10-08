package ru.amalnev.solarium.library.console;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("expect")
@FunctionArguments({"console", "pattern"})
public class Expect extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String pattern = getScalarArgument(context, "pattern");
            final IConsole console = getScalarArgument(context, "console");
            setReturnValue(context, console.expect(pattern));
        }
        catch (IOException e)
        {
            setReturnValue(context, null);
        }
    }
}
