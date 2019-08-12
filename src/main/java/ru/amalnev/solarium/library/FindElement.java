package ru.amalnev.solarium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import ru.amalnev.solarium.interpreter.ExecutionContext;

public abstract class FindElement extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final String parentArg = getClass().getAnnotation(FunctionArguments.class).value()[0];
        final String searchTermArg =  getClass().getAnnotation(FunctionArguments.class).value()[1];

        final SearchContext parentContext = context.getLocalVariableValue(parentArg, SearchContext.class);
        final String searchTerm = context.getLocalVariableValue(searchTermArg, String.class);
        setReturnValue(context, parentContext.findElement(getBy(searchTerm)));
    }

    protected abstract By getBy(String searchTerm);
}
