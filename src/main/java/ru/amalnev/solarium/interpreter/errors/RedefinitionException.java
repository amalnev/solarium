package ru.amalnev.solarium.interpreter.errors;

public class RedefinitionException extends InterpreterException
{
    public RedefinitionException(String name)
    {
        super(name + " : redefinition");
    }
}
