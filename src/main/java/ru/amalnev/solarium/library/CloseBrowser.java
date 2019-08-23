package ru.amalnev.solarium.library;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("close_browser")
@FunctionArguments({"browser"})
public class CloseBrowser extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context)
    {
        final WebDriver webDriver = context.getValue("browser", WebDriver.class);
        if (webDriver != null) webDriver.close();
    }
}
