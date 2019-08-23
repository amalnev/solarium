package ru.amalnev.solarium.library;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("get_attribute_value")
@FunctionArguments({"element", "attribute_name"})
public class GetAttribute extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final WebElement element = context.getValue("element", WebElement.class);
        final String attributeName = context.getValue("attribute_name", String.class);
        setReturnValue(context, element.getAttribute(attributeName));
    }
}
