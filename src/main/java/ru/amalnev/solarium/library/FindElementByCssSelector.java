package ru.amalnev.solarium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import ru.amalnev.solarium.interpreter.ExecutionContext;

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
