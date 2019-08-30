package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.Parser;
import ru.amalnev.solarium.language.ParserException;
import ru.amalnev.solarium.language.expressions.FunctionCallExpression;
import ru.amalnev.solarium.language.statements.FunctionDefinition;
import ru.amalnev.solarium.library.Library;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class Interpreter
{
    private final ExecutionContext executionContext;

    private final Library library;

    private final Parser parser;

    public Interpreter()
    {
        parser = new Parser();
        executionContext = new ExecutionContext();
        library = new Library();

        library.getNativeFunctions().forEach(executionContext::defineFunction);
    }

    private static String makeRandomIdentifier()
    {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public Object runFromString(final String sourceCode) throws IOException, ParserException
    {
        final FunctionDefinition entryPoint = parser.parse(new StringReader(sourceCode));
        entryPoint.setFunctionName(makeRandomIdentifier());
        executionContext.defineFunction(entryPoint);

        final FunctionCallExpression entryPointCall = new FunctionCallExpression();
        entryPointCall.setFunctionName(entryPoint.getFunctionName());
        return entryPointCall.evaluate(executionContext).getValue();
    }

    public Object runFromFile(final String sourceFilePath) throws IOException, ParserException
    {
        final Path sourcePath = Paths.get(sourceFilePath);
        if(!sourcePath.isAbsolute()) sourcePath.toAbsolutePath();
        final StringBuilder sourceCodeBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(sourcePath, StandardCharsets.UTF_8))
        {
            stream.forEach(s -> sourceCodeBuilder.append(s).append("\n"));
        }

        final String sourceCode = sourceCodeBuilder.toString();
        final Preprocessor preprocessor = new Preprocessor(sourcePath.getParent().toString());
        return runFromString(preprocessor.process(sourceCode));
    }
}
