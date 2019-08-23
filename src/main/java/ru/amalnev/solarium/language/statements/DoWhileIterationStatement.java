package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public class DoWhileIterationStatement extends IterationStatement
{
    @Override
    public ControlFlowInfluence execute(ExecutionContext context)
    {
        context.enterEnclosedScope();
        try
        {
            do
            {
                final ControlFlowInfluence result = getBody().execute(context);
                if (result == ControlFlowInfluence.EXIT_CURRENT_ITERATION) continue;
                if (result == ControlFlowInfluence.EXIT_CURRENT_BLOCK) break;
                if (result == ControlFlowInfluence.EXIT_CURRENT_FUNCTION) return result;
            }
            while ((Boolean)getCondition().evaluate(context).getValue());
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
        builder.append("do\n");
        builder.append(getBody().toString());
        builder.append("\n");
        builder.append("while(");
        builder.append(getCondition().toString());
        builder.append(");");
        return builder.toString();
    }
}
