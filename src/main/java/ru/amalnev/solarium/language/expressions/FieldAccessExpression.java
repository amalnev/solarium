package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;

@NoArgsConstructor
@AllArgsConstructor
public class FieldAccessExpression implements IExpression
{
    @Getter
    @Setter
    private IExpression sourceExpression;

    @Getter
    @Setter
    private String fieldName;

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        final IValue source = sourceExpression.evaluate(context);
        return source.getField(fieldName);
    }

    @Override
    public String toString()
    {
        return sourceExpression.toString() + "." + fieldName;
    }
}
