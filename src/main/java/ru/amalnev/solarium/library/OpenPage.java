package ru.amalnev.solarium.library;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("open_page")
@FunctionArguments({"browser", "url"})
public class OpenPage extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final WebDriver webDriver = context.getValue("browser", WebDriver.class);
        final String url = context.getValue("url", String.class);
        webDriver.get(url);
    }
}
