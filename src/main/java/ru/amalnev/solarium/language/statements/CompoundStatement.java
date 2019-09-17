package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CompoundStatement implements IStatement
{
    private List<IStatement> statements = new ArrayList<>();

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context) throws InterpreterException
    {
        context.enterEnclosedScope();
        try
        {
            for (final IStatement statement : statements)
            {
                final ControlFlowInfluence result = statement.execute(context);
                if (result != ControlFlowInfluence.NO_INFLUENCE)
                    return result;
            }

            return ControlFlowInfluence.NO_INFLUENCE;
        }
        finally
        {
            context.exitCurrentScope();
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        statements.forEach(statement -> {
            builder.append(statement);
            builder.append("\n");
        });
        builder.append("}");
        return builder.toString();
    }
}
