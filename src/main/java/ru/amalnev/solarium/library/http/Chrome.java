package ru.amalnev.solarium.library.http;

import org.openqa.selenium.chrome.ChromeDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("chrome")
@FunctionArguments("driverPath")
public class Chrome extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String driverPath = getScalarArgument(context, "driverPath");
        System.setProperty("webdriver.chrome.driver", driverPath);
        setReturnValue(context, new ChromeDriver());
    }
}
