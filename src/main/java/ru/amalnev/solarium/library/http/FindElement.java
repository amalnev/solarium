package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;

public abstract class FindElement extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String parentArg = getClass().getAnnotation(FunctionArguments.class).value()[0];
        final String searchTermArg = getClass().getAnnotation(FunctionArguments.class).value()[1];

        final SearchContext parentContext = getScalarArgument(context, parentArg);
        final String searchTerm = getScalarArgument(context, searchTermArg);

        try
        {
            setReturnValue(context, parentContext.findElement(getBy(searchTerm)));
        }
        catch (NoSuchElementException e)
        {
            setReturnValue(context, null);
        }
    }

    protected abstract By getBy(String searchTerm);
}
