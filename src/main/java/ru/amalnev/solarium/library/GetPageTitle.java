package ru.amalnev.solarium.library;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("get_current_page_title")
@FunctionArguments({"browser"})
public class GetPageTitle extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final WebDriver browser = context.getValue("browser", WebDriver.class);
        setReturnValue(context, browser.getTitle());
    }
}
