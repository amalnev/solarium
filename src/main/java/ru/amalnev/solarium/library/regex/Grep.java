package ru.amalnev.solarium.library.regex;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionName("grep")
@FunctionArguments({"pattern", "text"})
public class Grep extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String pattern = getScalarArgument(context, "pattern");
        final String text = getScalarArgument(context, "text");

        final Pattern regex = Pattern.compile(pattern);
        final Matcher matcher = regex.matcher(text);
        final IValue result = new Value().convertToArray();
        while (matcher.find())
        {
            final IValue matchResult = new Value();
            if (matcher.groupCount() > 0)
            {
                matchResult.convertToArray();
                for (int i = 0; i <= matcher.groupCount(); i++)
                {
                    final IValue groupResult = new Value();
                    groupResult.setScalarValue(matcher.group(i));
                    matchResult.addArrayElement(groupResult);
                }
            }
            else
            {
                matchResult.setScalarValue(matcher.group());
            }

            result.addArrayElement(matchResult);
        }

        context.setReturnValue(result);
    }
}
