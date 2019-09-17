package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("send_keys")
@FunctionArguments({"element", "keys"})
public class SendKeys extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final WebElement element = getScalarArgument(context, "element");
        final String keys = getScalarArgument(context, "keys");
        element.sendKeys(keys);
    }
}
