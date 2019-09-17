package ru.amalnev.solarium.interpreter.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TypeMismatchException extends InterpreterException
{
    public TypeMismatchException(String expr, String requiredType, String actualType)
    {
        super("Expression '" + expr + "' evaluates to " + actualType + ", while " + requiredType + " is required");
    }
}
