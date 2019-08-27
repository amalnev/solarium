package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.IValue;
import ru.amalnev.solarium.interpreter.RValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArrayLiteralExpression implements IExpression
{
    private List<IExpression> elements = new ArrayList<>();

    @Override
    public IValue evaluate(ExecutionContext context)
    {
        final List<Object> elementValues = new ArrayList<>();
        elements.forEach(element -> elementValues.add(element.evaluate(context).getValue()));
        return new RValue(elementValues);
    }
}
