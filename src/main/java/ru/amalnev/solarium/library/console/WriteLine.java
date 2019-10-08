package ru.amalnev.solarium.library.console;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("writeLine")
@FunctionArguments({"console", "line"})
public class WriteLine extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final IConsole console = getScalarArgument(context, "console");
            final String line = getScalarArgument(context, "line");
            console.writeLine(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
