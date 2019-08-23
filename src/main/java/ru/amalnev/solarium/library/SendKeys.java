package ru.amalnev.solarium.library;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("send_keys")
@FunctionArguments({"element", "keys"})
public class SendKeys extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final WebElement element = context.getValue("element", WebElement.class);
        final String keys = context.getValue("keys", String.class);
        element.sendKeys(keys);
    }
}
