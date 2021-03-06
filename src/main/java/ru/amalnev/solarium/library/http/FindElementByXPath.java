package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("findElementByXPath")
@FunctionArguments({"parent", "xpath"})
public class FindElementByXPath extends FindElement
{
    @Override
    protected By getBy(String searchTerm)
    {
        return By.xpath(searchTerm);
    }
}
