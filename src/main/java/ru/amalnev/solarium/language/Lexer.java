package ru.amalnev.solarium.language;

import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.io.StreamTokenizer.TT_EOL;

public class Lexer
{
    private static final Map<String, Short> keywordMappings = new HashMap<String, Short>()
    {{
        put("function", Parser.FUNCTION_KEYWORD);
        put("true", Parser.TRUE_KEYWORD);
        put("false", Parser.FALSE_KEYWORD);
        put("if", Parser.IF_KEYWORD);
        put("else", Parser.ELSE_KEYWORD);
        put("for", Parser.FOR_KEYWORD);
        put("return", Parser.RETURN_KEYWORD);
        put("break", Parser.BREAK_KEYWORD);
        put("null", Parser.NULL_KEYWORD);
    }};

    private static final Map<Integer, Short> delimiterMappings = new HashMap<Integer, Short>()
    {{
        put(33, Parser.NOT);
        put(38, Parser.AND);
        put(42, Parser.MUL);
        put(43, Parser.PLUS);
        put(45, Parser.MINUS);
        put(47, Parser.DIV);
        put(60, Parser.LT);
        put(61, Parser.EQ);
        put(62, Parser.GT);
        put(58, Parser.COLON);
        put(59, Parser.SEMICOLON);
        put(40, Parser.OPEN_BRACKET);
        put(41, Parser.CLOSE_BRACKET);
        put(44, Parser.COMMA);
        put(46, Parser.DOT);
        put(91, Parser.OPEN_SQUARE_BRACKET);
        put(93, Parser.CLOSE_SQUARE_BRACKET);
        put(123, Parser.OPEN_CURLY_BRACKET);
        put(124, Parser.OR);
        put(125, Parser.CLOSE_CURLY_BRACKET);
    }};

    private final StreamTokenizer tokenizer;

    private final Parser parser;

    @Getter
    private int lineNumber = 1;

    public Lexer(final Parser parser, final String inputString)
    {
        final Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputString.getBytes())));
        tokenizer = new StreamTokenizer(reader);
        this.parser = parser;

        tokenizer.eolIsSignificant(true);
        tokenizer.parseNumbers();
        tokenizer.quoteChar(34); //"
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.wordChars(97, 122); //a-z
        tokenizer.wordChars(65, 90);  //A-Z
        tokenizer.wordChars(48, 57); //0-9
        tokenizer.wordChars(95, 95); //_
        delimiterMappings.forEach((token, entity) -> tokenizer.ordinaryChar(token));
    }

    int yylex() throws IOException
    {
        int token = tokenizer.nextToken();
        if (token == StreamTokenizer.TT_WORD)
        {
            final Short keywordMapping = keywordMappings.get(tokenizer.sval);
            if (keywordMapping != null) return keywordMapping;

            parser.yylval = new ParserVal(tokenizer.sval);
            return Parser.WORD;
        }
        else if (token == StreamTokenizer.TT_NUMBER)
        {
            parser.yylval = new ParserVal((int) tokenizer.nval);
            return Parser.NUMERIC_LITERAL;
        }
        else if (token == 34)
        {
            parser.yylval = new ParserVal(tokenizer.sval);
            return Parser.STRING_LITERAL;
        }
        else if (delimiterMappings.keySet().contains(token))
        {
            //special handling of '=' due to the possibility of encountering '=='
            if (token == 61)
            {
                final int nextToken = tokenizer.nextToken();
                if (nextToken == 61)
                {
                    //'==' is encountered.
                    return Parser.DBLEQ;
                }
                else
                {
                    //'=' is encountered.
                    tokenizer.pushBack();
                    return Parser.EQ;
                }
            }
            else
            {
                return delimiterMappings.get(token);
            }
        }
        else if (token == TT_EOL)
        {
            lineNumber++;
            return yylex();
        }
        else if (token == StreamTokenizer.TT_EOF)
        {
            return 0;
        }

        return yylex();
    }
}
