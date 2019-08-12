package ru.amalnev.solarium.language.statements;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;

@NoArgsConstructor
@AllArgsConstructor
public class ReturnStatement extends AbstractStatement
{
    @Setter
    private IExpression what;

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        if (what != null)
        {
            final Object returnValue = what.evaluate(context);
            context.setReturnValue(returnValue);
        }

        return ControlFlowInfluence.EXIT_CURRENT_FUNCTION;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
