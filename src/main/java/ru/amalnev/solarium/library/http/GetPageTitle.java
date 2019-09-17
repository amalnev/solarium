package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("get_current_page_title")
@FunctionArguments({"browser"})
public class GetPageTitle extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final WebDriver browser = getScalarArgument(context, "browser");
        setReturnValue(context, browser.getTitle());
    }
}
