package ru.amalnev.solarium.interpreter.stack;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;

public interface IStackFrame
{
    void enterEnclosedScope();

    void exitCurrentScope();

    String getFunctionName() throws InterpreterException;

    void setFunctionName(String functionName) throws InterpreterException;

    void setReturnValue(IValue returnValue);

    IValue getReturnValue() throws InterpreterException;
}
