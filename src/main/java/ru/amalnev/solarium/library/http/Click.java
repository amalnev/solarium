package ru.amalnev.solarium.library.http;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("click")
@FunctionArguments({"browser", "element"})
public class Click extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final WebDriver browser = getScalarArgument(context, "browser");
        final WebElement element = getScalarArgument(context, "element");

        try
        {
            element.click();
        }
        catch (final ElementNotInteractableException e)
        {
            ((JavascriptExecutor) browser).executeScript("arguments[0].click();", element);
        }
    }
}
