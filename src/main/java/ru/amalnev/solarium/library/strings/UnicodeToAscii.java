package ru.amalnev.solarium.library.strings;

import gcardone.junidecode.Junidecode;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("unicodeToAscii")
@FunctionArguments("unicodeString")
public class UnicodeToAscii extends AbstractNativeFunction {
    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String unicodeString = getScalarArgument(context, "unicodeString");
        setReturnValue(context, Junidecode.unidecode(unicodeString));
    }
}
