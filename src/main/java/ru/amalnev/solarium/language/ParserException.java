package ru.amalnev.solarium.language;

import lombok.Getter;

public class ParserException extends Exception
{
    @Getter
    private String message;

    public ParserException(final String message, final Parser parser)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);

        if (parser.yys != null)
        {
            builder.append(" near ");
            builder.append(parser.yys);
        }

        builder.append(" at line ");
        builder.append(parser.getLexer().getLineNumber());

        this.message = builder.toString();
    }
}
