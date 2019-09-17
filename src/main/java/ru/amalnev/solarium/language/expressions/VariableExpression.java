package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.VariableNotDefinedException;
import ru.amalnev.solarium.interpreter.memory.IValue;

@Getter
@Setter
@AllArgsConstructor
public class VariableExpression implements IExpression
{
    private String name;

    public String toString()
    {
        return name;
    }

    @Override
    public IValue evaluate(final ExecutionContext context) throws InterpreterException
    {
        try
        {
            return context.findVariable(name);
        }
        catch (VariableNotDefinedException e)
        {
            return context.defineVariable(name);
        }
    }
}
