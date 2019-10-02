package ru.amalnev.solarium.library.common;

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
import java.util.ArrayList;

@FunctionName("writeFile")
@FunctionArguments({"file", "contents"})
public class WriteFile extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final String file = getScalarArgument(context, "file");
        final String contents = getScalarArgument(context, "contents");

        final Path filePath = Paths.get(file);
        try
        {
            Files.write(
                    filePath,
                    new ArrayList<String>()
                    {{ add(contents); }},
                    StandardCharsets.UTF_8);

        }
        catch (IOException e)
        {
            setReturnValue(context, false);
            return;
        }
        setReturnValue(context, true);
    }
}
