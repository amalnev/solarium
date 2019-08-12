package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import ru.amalnev.solarium.interpreter.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CodeBlock implements IStatement
{
    private List<IStatement> statements = new ArrayList<>();

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        for (final IStatement statement : statements)
        {
            final ControlFlowInfluence result = statement.execute(context);
            if (result != ControlFlowInfluence.NO_INFLUENCE)
                return result;
        }

        return ControlFlowInfluence.NO_INFLUENCE;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        statements.forEach(statement -> {
            builder.append(statement);
            builder.append("\n");
        });

        return builder.toString();
    }
}
