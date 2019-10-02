package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("selectByValue")
@FunctionArguments({"dropDownElement", "item"})
public class SelectByIndex extends SelectFromDropDown
{
    @Override
    void doSelect(WebElement dropDownElement, Object item)
    {
        final Select dropDown = new Select(dropDownElement);
        dropDown.selectByIndex((Integer) item);
    }
}
