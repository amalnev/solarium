package ru.amalnev.solarium.library.io;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@FunctionName("readFile")
@FunctionArguments("file")
public class ReadFile extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String file = getScalarArgument(context, "file");
            final Path filePath = Paths.get(file);
            final StringBuilder contentBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8))
            {
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
            }
            setReturnValue(context, contentBuilder.toString());
        }
        catch (IOException e)
        {
            throw new InterpreterException(e);
        }
    }
}
