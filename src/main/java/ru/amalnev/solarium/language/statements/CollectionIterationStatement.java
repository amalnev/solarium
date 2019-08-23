package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;

import java.util.List;

@Getter
@Setter
public class CollectionIterationStatement extends IterationStatement
{
    private String elementName;

    private IExpression collectionExpression;

    @Override
    public ControlFlowInfluence execute(ExecutionContext context)
    {
        context.enterEnclosedScope();
        context.defineScalar(elementName);
        try
        {
            List<Object> collection = (List<Object>) collectionExpression.evaluate(context).getValue();
            if(collection == null) return ControlFlowInfluence.NO_INFLUENCE;
            for(final Object element: collection)
            {
                context.setValue(elementName, element);
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
