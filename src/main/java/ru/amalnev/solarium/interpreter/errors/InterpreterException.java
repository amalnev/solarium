package ru.amalnev.solarium.interpreter.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InterpreterException extends Exception
{
    public InterpreterException(final String message)
    {
        super(message);
    }
}
