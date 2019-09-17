package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.Parser;
import ru.amalnev.solarium.language.ParserException;
import ru.amalnev.solarium.language.expressions.FunctionCallExpression;
import ru.amalnev.solarium.language.statements.FunctionDefinition;
import ru.amalnev.solarium.library.Library;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Interpreter
{
    private final ExecutionContext executionContext;

    private final Library library;

    private final Parser parser;

    public Interpreter() throws InterpreterException
    {
        parser = new Parser();
        executionContext = new ExecutionContext();
        library = new Library();

        for (FunctionDefinition functionDefinition : library.getNativeFunctions())
        {
            executionContext.defineFunction(functionDefinition);
        }
    }

    private static String makeRandomIdentifier()
    {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++)
        {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public Object runFromString(final String sourceCode) throws IOException, ParserException, InterpreterException
    {
        final FunctionDefinition entryPoint = parser.parse(new StringReader(sourceCode));
        entryPoint.setFunctionName(makeRandomIdentifier());
        executionContext.defineFunction(entryPoint);

        final FunctionCallExpression entryPointCall = new FunctionCallExpression();
        entryPointCall.setFunctionName(entryPoint.getFunctionName());
        final IValue result = entryPointCall.evaluate(executionContext);
        if (result == null) return null;
        return result.getScalarValue();
    }

    public Object runFromFile(final String sourceFilePath) throws IOException, ParserException, InterpreterException
    {
        final Path sourcePath = Paths.get(sourceFilePath);
        if (!sourcePath.isAbsolute()) sourcePath.toAbsolutePath();

        final Preprocessor preprocessor = new Preprocessor(sourcePath.getParent().toString());
        return runFromString(preprocessor.processFile(sourcePath.toString()));
    }
}
