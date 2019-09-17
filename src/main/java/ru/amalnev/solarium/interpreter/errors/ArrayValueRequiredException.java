package ru.amalnev.solarium.interpreter.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArrayValueRequiredException extends InterpreterException
{
    public ArrayValueRequiredException(String varname)
    {
        super("Array value is required, but '" + varname + "' is not an array.");
    }
}
