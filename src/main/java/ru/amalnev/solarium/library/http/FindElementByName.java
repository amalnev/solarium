package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("findElementByName")
@FunctionArguments({"parent", "name"})
public class FindElementByName extends FindElement {

    @Override
    protected By getBy(String searchTerm) {
        return By.name(searchTerm);
    }
}
