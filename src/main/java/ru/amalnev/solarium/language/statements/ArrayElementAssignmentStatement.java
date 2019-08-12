package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.LocalVariable;
import ru.amalnev.solarium.language.expressions.ArrayDereferenceExpression;
import ru.amalnev.solarium.language.expressions.Variable;

import java.util.List;

public class ArrayElementAssignmentStatement extends AssignmentStatement
{
    @Override
    public ControlFlowInfluence execute(ExecutionContext context)
    {
        final ArrayDereferenceExpression arrayDereferenceExpression = (ArrayDereferenceExpression) getLeftHandOperand();
        final LocalVariable arrayLocalVariable = context.getLocalVariable(((Variable) arrayDereferenceExpression.getArrayExpression()).getName());
        final List<Object> arrayValue = (List<Object>) arrayLocalVariable.getValue();
        final Integer index = (Integer) arrayDereferenceExpression.getIndexExpression().evaluate(context);
        arrayValue.set(index, getRightHandOperand().evaluate(context));
        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
