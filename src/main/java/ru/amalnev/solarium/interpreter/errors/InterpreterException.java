package ru.amalnev.solarium.interpreter.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InterpreterException extends Exception
{
    public InterpreterException(String message)
    {
        super(message);
    }

    public InterpreterException(Exception cause)
    {
        super(cause);
    }
}
