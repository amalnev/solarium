package ru.amalnev.solarium.interpreter.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IndexOutOfBoundsException extends InterpreterException
{
    public IndexOutOfBoundsException(String varname, Integer size, Integer index)
    {
        super("Index '" + index + "' is out of bounds for array '" + varname + "'. It's size is '" + size + "'");
    }
}
