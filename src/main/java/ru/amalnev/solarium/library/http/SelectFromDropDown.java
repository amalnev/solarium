package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;

public abstract class SelectFromDropDown extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String dropDownElementArg = getClass().getAnnotation(FunctionArguments.class).value()[0];
        final String itemElementArg = getClass().getAnnotation(FunctionArguments.class).value()[1];

        final WebElement dropDownElement = getScalarArgument(context, dropDownElementArg);
        final Object itemElement = getScalarArgument(context, itemElementArg);

        doSelect(dropDownElement, itemElement);
    }

    abstract void doSelect(WebElement dropDownElement, Object item);
}
