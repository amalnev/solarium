package ru.amalnev.solarium.language.statements;

public class WhileIterationStatement extends IterationStatement
{
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("while (");
        builder.append(getCondition().toString());
        builder.append(")\n");
        builder.append(getBody().toString());
        return builder.toString();
    }
}
