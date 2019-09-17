package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.RedefinitionException;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;

import java.util.List;

@Getter
@Setter
public class FunctionDefinition implements IStatement
{
    private String functionName;

    private List<String> argumentNames = new CommaSeparatedList<>();

    private CompoundStatement body;

    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        if (functionName != null && !functionName.isEmpty())
        {
            builder.append("function ");
            builder.append(functionName);
            builder.append("(");
            builder.append(argumentNames.toString());

            builder.append(")\n{\n");
        }

        builder.append(body.toString());

        if (functionName != null && !functionName.isEmpty())
        {
            builder.append("};");
        }

        return builder.toString();
    }

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context) throws RedefinitionException
    {
        context.defineFunction(this);
        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
