package ru.amalnev.solarium.interpreter.errors;

public class UnknownIdentifier extends InterpreterException
{
    public UnknownIdentifier(String identifier)
    {
        super("Unknown identifier: " + identifier);
    }
}
