package ru.amalnev.solarium.interpreter;

public class RedefinitionException extends InterpreterException
{
    public RedefinitionException(String name)
    {
        super(name + " : redefinition");
    }
}
