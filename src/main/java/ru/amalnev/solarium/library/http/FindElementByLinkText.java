package ru.amalnev.solarium.library.http;

import org.openqa.selenium.By;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("findElementByLinkText")
@FunctionArguments({"parent", "name"})
public class FindElementByLinkText extends FindElement {

    @Override
    protected By getBy(String searchTerm) {
        return By.linkText(searchTerm);
    }
}
