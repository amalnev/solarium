package ru.amalnev.solarium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@FunctionName("find_element_by_id")
@FunctionArguments({"parent", "id"})
public class FindElementById extends FindElement
{
    @Override
    protected By getBy(String searchTerm)
    {
        return By.id(searchTerm);
    }
}
