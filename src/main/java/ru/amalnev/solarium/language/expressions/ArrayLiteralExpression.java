package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;

import java.util.List;

@Getter
@Setter
public class ArrayLiteralExpression implements IExpression
{
    private List<IExpression> elements = new CommaSeparatedList<>();

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        final Value value = new Value();
        value.convertToArray();
        for (IExpression element : elements)
        {
            value.addArrayElement(element.evaluate(context));
        }

        return value;
    }

    @Override
    public String toString()
    {
        return "[" + elements.toString() + "]";
    }
}
