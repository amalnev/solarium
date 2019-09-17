package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.expressions.IExpression;

@Getter
@Setter
public class CollectionIterationStatement extends IterationStatement
{
    private String elementName;

    private IExpression collectionExpression;

    @Override
    public ControlFlowInfluence execute(ExecutionContext context) throws InterpreterException
    {
        context.enterEnclosedScope();
        context.defineVariable(elementName);
        try
        {
            IValue collection = collectionExpression.evaluate(context);
            if (collection == null) return ControlFlowInfluence.NO_INFLUENCE;
            for (int i = 0; i < collection.getArraySize(); i++)
            {
                final IValue element = collection.getArrayElement(i);
                context.findVariable(elementName).copy(element);
                final ControlFlowInfluence result = getBody().execute(context);
                if (result == ControlFlowInfluence.EXIT_CURRENT_ITERATION) continue;
                if (result == ControlFlowInfluence.EXIT_CURRENT_BLOCK) break;
                if (result == ControlFlowInfluence.EXIT_CURRENT_FUNCTION) return result;
            }
        }
        finally
        {
            context.exitCurrentScope();
        }

        return ControlFlowInfluence.NO_INFLUENCE;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
