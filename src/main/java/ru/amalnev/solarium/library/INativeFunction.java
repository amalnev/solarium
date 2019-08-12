package ru.amalnev.solarium.library;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public interface INativeFunction
{
    void call(ExecutionContext context);
}
