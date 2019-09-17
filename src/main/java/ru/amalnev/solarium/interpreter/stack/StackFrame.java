package ru.amalnev.solarium.interpreter.stack;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.IVariableScope;
import ru.amalnev.solarium.interpreter.memory.VariableScope;

public class StackFrame implements IVariableScope, IStackFrame
{
    @Getter
    @Setter
    private String functionName;

    @Getter
    private VariableScope currentScope = new VariableScope();

    private IValue returnValue;

    @Override
    public void enterEnclosedScope()
    {
        final VariableScope enclosedScope = new VariableScope();
        enclosedScope.setParentScope(currentScope);
        currentScope = enclosedScope;
    }

    @Override
    public void exitCurrentScope()
    {
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void setReturnValue(IValue returnValue)
    {
        this.returnValue = returnValue;
    }

    @Override
    public IValue getReturnValue()
    {
        return returnValue;
    }

    @Override
    public IValue defineVariable(String name)
    {
        return currentScope.defineVariable(name);
    }

    @Override
    public IValue findVariable(String name) throws InterpreterException
    {
        return currentScope.findVariable(name);
    }
}
