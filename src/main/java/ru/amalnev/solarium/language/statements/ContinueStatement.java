package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public class ContinueStatement implements IStatement
{
    @Override
    public ControlFlowInfluence execute(ExecutionContext context)
    {
        return ControlFlowInfluence.EXIT_CURRENT_ITERATION;
    }
}
