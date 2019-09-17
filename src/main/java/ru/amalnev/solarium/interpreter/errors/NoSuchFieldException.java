package ru.amalnev.solarium.interpreter.errors;

public class NoSuchFieldException extends InterpreterException
{
    public NoSuchFieldException(String varname, String fieldName)
    {
        super("Value '" + varname + "' has no field named '" + fieldName + "'");
    }
}
