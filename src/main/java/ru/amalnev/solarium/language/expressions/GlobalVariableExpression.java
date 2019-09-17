package ru.amalnev.solarium.language.expressions;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.VariableNotDefinedException;
import ru.amalnev.solarium.interpreter.memory.IValue;

public class GlobalVariableExpression extends VariableExpression
{
    public GlobalVariableExpression(String name)
    {
        super(name);
    }

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        try
        {
            return context.findVariable(getName());
        }
        catch (VariableNotDefinedException e)
        {
            return context.defineGlobalVariable(getName());
        }
    }
}
