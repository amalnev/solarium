package ru.amalnev.solarium.language.statements;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;

/**
 * This is a representation of a conditional statement (i.e if-else).
 *
 * @author Alexei Malnev
 */
@AllArgsConstructor
public class SelectionStatement implements IStatement
{
    @Setter
    private IExpression condition;

    @Setter
    private IStatement positiveStatement;

    @Setter
    private IStatement negativeStatement;

    public SelectionStatement(IExpression condition, IStatement positiveStatement)
    {
        this.condition = condition;
        this.positiveStatement = positiveStatement;
    }

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        final Boolean conditionValue = (Boolean) condition.evaluate(context).getValue();
        final IStatement statementToExecute = conditionValue ? positiveStatement : negativeStatement;
        if (statementToExecute != null) return statementToExecute.execute(context);
        return ControlFlowInfluence.NO_INFLUENCE;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("if (");
        builder.append(condition.toString());
        builder.append(")\n");

        if (positiveStatement != null) builder.append(positiveStatement.toString());

        if (negativeStatement != null)
        {
            builder.append("\nelse\n");
            builder.append(negativeStatement.toString());
        }

        return builder.toString();
    }
}
