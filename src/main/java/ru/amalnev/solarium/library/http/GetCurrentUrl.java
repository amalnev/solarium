package ru.amalnev.solarium.library.http;

import org.openqa.selenium.WebDriver;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("getCurrentUrl")
@FunctionArguments({"browser"})
public class GetCurrentUrl extends AbstractNativeFunction {

    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final WebDriver webDriver = getScalarArgument(context, "browser");
        setReturnValue(context, webDriver.getCurrentUrl());
    }
}
