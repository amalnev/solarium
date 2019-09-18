package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("getAttribute")
@FunctionArguments({"element", "attributeName"})
public class GetAttribute extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final WebElement element = getScalarArgument(context, "element");
        final String attributeName = getScalarArgument(context, "attributeName");
        setReturnValue(context, element.getAttribute(attributeName));
    }
}
