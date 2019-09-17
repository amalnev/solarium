package ru.amalnev.solarium.interpreter.errors;

public class LValueRequiredException extends InterpreterException
{
    public LValueRequiredException(String expr)
    {
        super(expr + " does not evaluate to lvalue");
    }
}
