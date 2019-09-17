package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.statements.IStatement;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;

import java.util.List;

public class FunctionCallExpression implements IExpression
{
    @Getter
    @Setter
    private List<IExpression> functionCallArguments = new CommaSeparatedList<>();

    @Getter
    @Setter
    private String functionName;

    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(functionName);
        builder.append("(");
        builder.append(functionCallArguments.toString());
        builder.append(")");
        return builder.toString();
    }

    @Override
    public IValue evaluate(final ExecutionContext context) throws InterpreterException
    {
        final IValue[] functionCallArgumentValues = new IValue[functionCallArguments.size()];
        for (int i = 0; i < functionCallArguments.size(); i++)
        {
            functionCallArgumentValues[i] = functionCallArguments.get(i).evaluate(context);
        }

        final IStatement functionBody = context.enterFunction(functionName, functionCallArgumentValues);
        if (functionBody != null) functionBody.execute(context);
        return context.exitFunction();
    }
}
