package ru.amalnev.solarium;

import lombok.extern.slf4j.Slf4j;
import ru.amalnev.solarium.interpreter.Interpreter;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.ParserException;

import java.io.IOException;

//TODO: FromJson integration
//TODO: Parser & interpreter error reporting
//TODO: Lambda expressions and closures
//TODO: preprocessor conditional directives (#ifdef, #ifndef)
//TODO: standard library tests
//TODO: fix single line comments
//TODO: interactive mode (?)
@Slf4j
public class App
{
    public static void main(String[] args) throws IOException, ParserException, InterpreterException
    {
        (new Interpreter()).runFromFile(args[0]);
        log.info("Script execution is over");
    }
}
