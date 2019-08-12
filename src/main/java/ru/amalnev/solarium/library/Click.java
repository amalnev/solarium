package ru.amalnev.solarium.library;

import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("click")
@FunctionArguments("element")
public class Click extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        context.getLocalVariableValue("element", WebElement.class).click();
    }
}
