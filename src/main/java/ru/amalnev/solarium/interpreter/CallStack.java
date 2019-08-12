package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.Stack;

@Getter
@Setter
public class CallStack
{
    private Stack<StackFrame> frames = new Stack<>();

    StackFrame topFrame()
    {
        return frames.peek();
    }

    public LocalVariable defineLocalVariable(final String name)
    {
        return topFrame().defineLocalVariable(name);
    }

    public LocalVariable defineLocalVariable(final String name, final Object value)
    {
        return topFrame().defineLocalVariable(name, value);
    }

    public void undefineLocalVariable(final String name)
    {
        topFrame().undefineLocalVariable(name);
    }

    public LocalVariable getLocalVariable(final String name)
    {
        return topFrame().getLocalVariable(name);
    }

    public void enterFunction(final FunctionDefinition functionDefinition, final Object... args)
    {
        final StackFrame stackFrame = StackFrame.makeFrameForFunctionCall(functionDefinition, args);
        frames.push(stackFrame);
    }

    public Object exitFunction()
    {
        final StackFrame topFrame = frames.pop();
        final String functionName = topFrame.getFunctionName();
        return topFrame.getLocalVariable(functionName).getValue();
    }

    public void setReturnValue(final Object value)
    {
        final String functionName = topFrame().getFunctionName();
        topFrame().getLocalVariable(functionName).setValue(value);
    }

    public boolean isVariableDefined(final String name)
    {
        return topFrame().isVariableDefined(name);
    }
}
