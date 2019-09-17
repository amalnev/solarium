package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("open_page")
@FunctionArguments({"browser", "url"})
public class OpenPage extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final WebDriver webDriver = getScalarArgument(context, "browser");
        final String url = getScalarArgument(context, "url");
        webDriver.get(url);
    }
}
