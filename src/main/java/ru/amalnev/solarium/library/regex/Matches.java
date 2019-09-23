package ru.amalnev.solarium.library.regex;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.util.regex.Pattern;

@FunctionName("matches")
@FunctionArguments({"text", "pattern"})
public class Matches extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String text = getScalarArgument(context, "text");
        final String pattern = getScalarArgument(context, "pattern");
        final Pattern regex = Pattern.compile(pattern);
        setReturnValue(context, regex.matcher(text).matches());
    }
}
