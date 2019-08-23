package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.Parser;
import ru.amalnev.solarium.language.ParserException;
import ru.amalnev.solarium.language.expressions.FunctionCallExpression;
import ru.amalnev.solarium.language.statements.FunctionDefinition;
import ru.amalnev.solarium.library.Library;

import java.io.*;

public class Program
{
    public static Object runFromString(final String sourceCode) throws IOException, ParserException
    {
        final Parser parser = new Parser();
        final FunctionDefinition entryPoint = parser.parse(new StringReader(sourceCode));
        final ExecutionContext executionContext = new ExecutionContext();

        final Library library = new Library();
        library.getNativeFunctions().forEach(executionContext::defineFunction);

        executionContext.defineFunction(entryPoint);

        final FunctionCallExpression entryPointCall = new FunctionCallExpression();
        entryPointCall.setFunctionName(entryPoint.getFunctionName());
        return entryPointCall.evaluate(executionContext).getValue();
    }

    public static Object runFromFile(final String sourceFilePath) throws IOException, ParserException
    {
        final InputStream inputStream = new FileInputStream(sourceFilePath);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder sourceCode = new StringBuilder();
        while (reader.ready())
        {
            sourceCode.append(reader.readLine());
        }

        return runFromString(sourceCode.toString());
    }
}
