package ru.amalnev.solarium;

import ru.amalnev.solarium.interpreter.Program;
import ru.amalnev.solarium.language.ParserException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

public class App
{
    public static void main(String[] args) throws IOException, ParserException
    {
        Path sourceFilePath = Paths.get(args[0]);
        if(!sourceFilePath.isAbsolute())
        {
            sourceFilePath = sourceFilePath.toAbsolutePath();
        }
        Program.runFromFile(sourceFilePath.toString());
    }
}
