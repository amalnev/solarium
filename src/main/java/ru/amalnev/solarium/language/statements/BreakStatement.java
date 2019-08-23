package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public class BreakStatement implements IStatement
{
    @Override
    public ControlFlowInfluence execute(ExecutionContext context)
    {
        return ControlFlowInfluence.EXIT_CURRENT_BLOCK;
    }
}
