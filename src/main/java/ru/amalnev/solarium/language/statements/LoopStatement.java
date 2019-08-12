package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.expressions.IExpression;
import ru.amalnev.solarium.language.expressions.Variable;

import java.util.Collection;

@Getter
@Setter
public class LoopStatement extends AbstractStatement
{
    private Variable loopVariable;

    private IExpression loopCollection;

    private CodeBlock loopBody;

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        context.defineLocalVariable(loopVariable.getName());
        try
        {
            Collection<?> collection = (Collection<?>) loopCollection.evaluate(context);

            for (final Object element : collection)
            {
                context.getLocalVariable(loopVariable.getName()).setValue(element);
                final ControlFlowInfluence result = loopBody.execute(context);
                if (result == ControlFlowInfluence.EXIT_CURRENT_BLOCK) break;
                if (result == ControlFlowInfluence.EXIT_CURRENT_FUNCTION) return result;
            }
        }
        finally
        {
            context.undefineLocalVariable(loopVariable.getName());
        }

        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
