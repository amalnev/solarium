package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.LocalVariable;
import ru.amalnev.solarium.language.expressions.Variable;

public class VariableAssignmentStatement extends AssignmentStatement
{
    public String toString()
    {
        return getLeftHandOperand().toString() + " = " + getRightHandOperand().toString() + ";";
    }

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context)
    {
        final Variable variable = (Variable) getLeftHandOperand();

        if (!context.isVariableDefined(variable.getName()))
            context.defineLocalVariable(variable.getName());

        final LocalVariable localVariable = context.getLocalVariable(variable.getName());
        localVariable.setValue(getRightHandOperand().evaluate(context));
        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
