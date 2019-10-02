package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("selectByVisibleName")
@FunctionArguments({"dropDownElement", "item"})
public class SelectByVisibleName extends SelectFromDropDown
{
    @Override
    void doSelect(WebElement dropDownElement, Object item)
    {
        final Select dropDown = new Select(dropDownElement);
        dropDown.selectByVisibleText((String) item);
    }
}
