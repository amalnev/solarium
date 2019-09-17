package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface IVariableScope
{
    IValue defineVariable(String name);

    IValue findVariable(String name) throws InterpreterException;
}
