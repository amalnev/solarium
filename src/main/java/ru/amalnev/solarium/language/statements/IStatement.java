package ru.amalnev.solarium.language.statements;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public interface IStatement
{
    enum ControlFlowInfluence
    {
        NO_INFLUENCE,
        EXIT_CURRENT_BLOCK,
        EXIT_CURRENT_FUNCTION,
        EXIT_CURRENT_ITERATION
    }

    ControlFlowInfluence execute(ExecutionContext context);
}
