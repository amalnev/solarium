package ru.amalnev.solarium.language.statements;

import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;

/**
 * This is a representation of a conditional statement (i.e if-else).
 *
 * @author Alexei Malnev
 */
public class ConditionalStatement extends AbstractStatement
{
    @Setter
    private IExpression condition;

    @Setter
    private CodeBlock positiveStatements;

    @Setter
    private CodeBlock negativeStatements;

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        final Boolean conditionValue = (Boolean) condition.evaluate(context);
        final CodeBlock blockToExecute = conditionValue ? positiveStatements : negativeStatements;
        if (blockToExecute != null) return blockToExecute.execute(context);
        return ControlFlowInfluence.NO_INFLUENCE;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("if (");
        builder.append(condition.toString());
        builder.append(")\n{\n");

        if (positiveStatements != null) builder.append(positiveStatements.toString());
        builder.append("}");

        if (negativeStatements != null)
        {
            builder.append("\nelse\n{\n");
            builder.append(negativeStatements.toString());
            builder.append("}");
        }

        builder.append(";");
        return builder.toString();
    }
}
