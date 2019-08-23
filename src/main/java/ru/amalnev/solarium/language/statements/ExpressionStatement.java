package ru.amalnev.solarium.language.statements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;

@NoArgsConstructor
@AllArgsConstructor
public class ExpressionStatement implements IStatement
{
    @Getter
    @Setter
    private IExpression expression;

    public String toString()
    {
        return expression.toString() + ";";
    }

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        expression.evaluate(context);
        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
