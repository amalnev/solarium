package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.language.statements.CompoundStatement;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.Stack;

@Getter
@Setter
public class CallStack implements IVariableScope, ICallStack, IStackFrame
{
    private Stack<StackFrame> frames = new Stack<>();

    private FunctionRepository functionRepository;

    StackFrame topFrame()
    {
        return frames.peek();
    }

    @Override
    public CompoundStatement enterFunction(String functionName, Object... args)
    {
        final FunctionDefinition functionDefinition = functionRepository.getFunctionDefinition(functionName);
        if(functionDefinition == null) throw new UnknownIdentifier(functionName);
        final StackFrame stackFrame = StackFrame.makeFrameForFunctionCall(functionDefinition, args);
        frames.push(stackFrame);
        return functionDefinition.getBody();
    }

    public Object exitFunction()
    {
        return frames.pop().getReturnValue();
    }

    public void setReturnValue(final Object value)
    {
        topFrame().setReturnValue(value);
    }

    @Override
    public void defineScalar(String name)
    {
        topFrame().defineScalar(name);
    }

    @Override
    public void defineArray(String name)
    {
        topFrame().defineArray(name);
    }

    @Override
    public Object getValue(String name)
    {
        return topFrame().getValue(name);
    }

    @Override
    public Object getValue(String name, Integer index)
    {
        return topFrame().getValue(name, index);
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass)
    {
        return topFrame().getValue(name, valueClass);
    }

    @Override
    public <T> T getValue(String name, Integer index, Class<T> valueClass)
    {
        return topFrame().getValue(name, index, valueClass);
    }

    @Override
    public void setValue(String name, Object value)
    {
        topFrame().setValue(name, value);
    }

    @Override
    public void setValue(String name, Integer index, Object value)
    {
        topFrame().setValue(name, index, value);
    }

    @Override
    public void enterEnclosedScope()
    {
        topFrame().enterEnclosedScope();
    }

    @Override
    public void exitCurrentScope()
    {
        topFrame().exitCurrentScope();
    }
}
