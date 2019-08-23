package ru.amalnev.solarium.interpreter;

public class VariableNotDefinedException extends InterpreterException
{
    public VariableNotDefinedException(String variableName)
    {
        super("Variable is not defined in the current scope: " + variableName);
    }
}
