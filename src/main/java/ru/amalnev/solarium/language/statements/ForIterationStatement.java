package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.expressions.IExpression;

@Getter
@Setter
public class ForIterationStatement extends IterationStatement
{
    private IStatement initializer;

    private IExpression postIterationExpression;

    @Override
    public ControlFlowInfluence execute(ExecutionContext context) throws InterpreterException
    {
        context.enterEnclosedScope();
        try
        {
            initializer.execute(context);
            while ((Boolean) getCondition().evaluate(context).getScalarValue())
            {
                final ControlFlowInfluence result = getBody().execute(context);
                if (result == ControlFlowInfluence.EXIT_CURRENT_ITERATION) continue;
                if (result == ControlFlowInfluence.EXIT_CURRENT_BLOCK) break;
                if (result == ControlFlowInfluence.EXIT_CURRENT_FUNCTION) return result;
                if (postIterationExpression != null) postIterationExpression.evaluate(context);
            }
        }
        finally
        {
            context.exitCurrentScope();
        }

        return ControlFlowInfluence.NO_INFLUENCE;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("for(");
        builder.append(initializer.toString());
        builder.append("; ");
        builder.append(getCondition().toString());
        builder.append("; ");
        if (postIterationExpression != null)
            builder.append(postIterationExpression.toString());
        builder.append(")\n");
        builder.append(getBody().toString());
        return builder.toString();
    }
}
