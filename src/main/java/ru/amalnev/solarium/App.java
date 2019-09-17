package ru.amalnev.solarium;

import ru.amalnev.solarium.interpreter.Interpreter;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.ParserException;

import java.io.IOException;

//TODO: Json integration
//TODO: Parser & interpreter error reporting
//TODO: Lambda expressions and closures
//TODO: Make sure build works on Windows and MacOS
public class App
{
    public static void main(String[] args) throws IOException, ParserException, InterpreterException
    {
        (new Interpreter()).runFromFile(args[0]);
    }
}
