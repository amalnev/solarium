package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("find_element_by_css_selector")
@FunctionArguments({"parent", "selector"})
public class FindElementByCssSelector extends FindElement
{
    @Override
    protected By getBy(String searchTerm)
    {
        return By.cssSelector(searchTerm);
    }
}
