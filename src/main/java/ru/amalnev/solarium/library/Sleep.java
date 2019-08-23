package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("sleep")
@FunctionArguments({"duration"})
public class Sleep extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final Integer duration = context.getValue("duration", Integer.class);
        try
        {
            Thread.sleep(duration);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
