package ru.amalnev.solarium.language.statements;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.expressions.IExpression;

@NoArgsConstructor
@AllArgsConstructor
public class ReturnStatement implements IStatement
{
    @Setter
    private IExpression what;

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context) throws InterpreterException
    {
        if (what != null)
        {
            context.setReturnValue(what.evaluate(context));
        }

        return ControlFlowInfluence.EXIT_CURRENT_FUNCTION;
    }

    @Override
    public String toString()
    {
        return "return " + what.toString();
    }
}
