package ru.amalnev.solarium.library;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("chrome")
@FunctionArguments("driverPath")
public class Chrome extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context)
    {
        final String driverPath = (String) context.getValue("driverPath");
        System.setProperty("webdriver.chrome.driver", driverPath);
        setReturnValue(context, new ChromeDriver());
    }
}
