package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("findElementById")
@FunctionArguments({"parent", "id"})
public class FindElementById extends FindElement
{
    @Override
    protected By getBy(String searchTerm)
    {
        return By.id(searchTerm);
    }
}
