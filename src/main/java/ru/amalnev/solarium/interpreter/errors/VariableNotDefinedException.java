package ru.amalnev.solarium.interpreter.errors;

public class VariableNotDefinedException extends InterpreterException
{
    public VariableNotDefinedException(String variableName)
    {
        super("Value is not defined in the current scope: " + variableName);
    }
}
