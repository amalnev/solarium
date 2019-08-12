package ru.amalnev.solarium.library;

import org.openqa.selenium.By;

@FunctionName("find_element_by_xpath")
@FunctionArguments({"parent", "xpath"})
public class FindElementByXPath extends FindElement
{
    @Override
    protected By getBy(String searchTerm)
    {
        return By.xpath(searchTerm);
    }
}
