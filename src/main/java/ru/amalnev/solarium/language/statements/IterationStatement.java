package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.expressions.IExpression;

@Getter
@Setter
public abstract class IterationStatement implements IStatement
{
    private IExpression condition;

    private IStatement body;

    @Override
    public ControlFlowInfluence execute(ExecutionContext context) throws InterpreterException
    {
        context.enterEnclosedScope();
        try
        {
            while ((Boolean) condition.evaluate(context).getScalarValue())
            {
                final ControlFlowInfluence result = body.execute(context);
                if (result == ControlFlowInfluence.EXIT_CURRENT_ITERATION) continue;
                if (result == ControlFlowInfluence.EXIT_CURRENT_BLOCK) break;
                if (result == ControlFlowInfluence.EXIT_CURRENT_FUNCTION) return result;
            }
        }
        finally
        {
            context.exitCurrentScope();
        }

        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
