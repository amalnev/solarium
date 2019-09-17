package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface INativeFunction
{
    void call(ExecutionContext context) throws InterpreterException;
}
