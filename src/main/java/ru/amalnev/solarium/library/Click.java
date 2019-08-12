package ru.amalnev.solarium.library;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("click")
@FunctionArguments({"browser","element"})
public class Click extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final WebDriver browser = context.getLocalVariableValue("browser", WebDriver.class);
        final WebElement element = context.getLocalVariableValue("element", WebElement.class);

        try
        {
            element.click();
        }
        catch(final ElementNotInteractableException e)
        {
            ((JavascriptExecutor)browser).executeScript("arguments[0].click();", element);
        }
    }
}
