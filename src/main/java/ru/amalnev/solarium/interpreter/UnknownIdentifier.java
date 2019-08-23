package ru.amalnev.solarium.interpreter;

public class UnknownIdentifier extends InterpreterException
{
    public UnknownIdentifier(String identifier)
    {
        super("Unknown identifier: " + identifier);
    }
}
