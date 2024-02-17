package ru.amalnev.solarium.library.http;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@FunctionName("firefox")
//@FunctionArguments({"driverPath"})
public class Firefox extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context)
    {
        /*final String driverPath = (String) context.getLocalVariable("driverPath").getValue();
        if(driverPath == null)
        {*/
        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.of(10, ChronoUnit.SECONDS));
        /*}
        else
        {
            System.setProperty("webdriver.firefox.driver", driverPath);
        }*/

        setReturnValue(context, driver);
    }
}
