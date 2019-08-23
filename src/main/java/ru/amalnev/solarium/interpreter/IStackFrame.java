package ru.amalnev.solarium.interpreter;

public interface IStackFrame
{
    void enterEnclosedScope();

    void exitCurrentScope();
}
