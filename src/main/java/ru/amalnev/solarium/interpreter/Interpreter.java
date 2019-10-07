package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.Parser;
import ru.amalnev.solarium.language.ParserException;
import ru.amalnev.solarium.language.expressions.FunctionCallExpression;
import ru.amalnev.solarium.language.statements.FunctionDefinition;
import ru.amalnev.solarium.language.utils.RandomIdentifier;
import ru.amalnev.solarium.library.Library;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public Object runFromString(String sourceCode) throws IOException, ParserException, InterpreterException
    {
        final FunctionDefinition entryPoint = parser.parse(new StringReader(sourceCode));
        entryPoint.setFunctionName(new RandomIdentifier().toString());
        executionContext.defineFunction(entryPoint);

        final FunctionCallExpression entryPointCall = new FunctionCallExpression();
        entryPointCall.setFunctionName(entryPoint.getFunctionName());
        final IValue result = entryPointCall.evaluate(executionContext);
        if (result == null) return null;
        return result.getScalarValue();
    }

    public Object runFromFile(final String sourceFilePath) throws IOException, ParserException, InterpreterException
    {
        Path sourcePath = Paths.get(sourceFilePath);
        if (!sourcePath.isAbsolute()) sourcePath = sourcePath.toAbsolutePath();

        final Preprocessor preprocessor = new Preprocessor(sourcePath.getParent().toString());
        return runFromString(preprocessor.processFile(sourcePath.toString()));
    }
}
