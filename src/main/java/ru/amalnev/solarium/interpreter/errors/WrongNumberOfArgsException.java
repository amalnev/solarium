package ru.amalnev.solarium.interpreter.errors;

public class WrongNumberOfArgsException extends InterpreterException
{
    public WrongNumberOfArgsException(String functionName, int argsRequired, int argsSupplied)
    {
        super("Wrong number of arguments for a call to '" + functionName + "'. Required: " + argsRequired + ", supplied: " + argsSupplied);
    }
}
