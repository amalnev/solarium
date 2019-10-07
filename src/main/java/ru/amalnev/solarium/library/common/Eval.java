package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.Parser;
import ru.amalnev.solarium.language.ParserException;
import ru.amalnev.solarium.language.statements.ExpressionStatement;
import ru.amalnev.solarium.language.statements.FunctionDefinition;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;
import java.io.StringReader;

@FunctionName("eval")
@FunctionArguments("code")
public class Eval extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        String code = getScalarArgument(context, "code");
        if (!code.endsWith(";")) code = code + ";";

        try
        {
            final FunctionDefinition parsedCode = new Parser().parse(new StringReader(code));
            if (parsedCode.getBody().getStatements().size() != 1) throw new InterpreterException();
            if (!(parsedCode.getBody().getStatements().get(0) instanceof ExpressionStatement))
                throw new InterpreterException();

            final ExpressionStatement expressionStatement = (ExpressionStatement) parsedCode.getBody().getStatements().get(0);


            final IValue result = expressionStatement.getExpression().evaluate(context);
            context.setReturnValue(result);
        }
        catch (ParserException | IOException e)
        {
            throw new InterpreterException(e);
        }
    }
}
