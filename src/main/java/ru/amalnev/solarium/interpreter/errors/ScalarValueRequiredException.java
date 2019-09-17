package ru.amalnev.solarium.interpreter.errors;

public class ScalarValueRequiredException extends InterpreterException
{
    public ScalarValueRequiredException(String varname)
    {
        super("Scalar value is required, but '" + varname + "' is an array");
    }
}
