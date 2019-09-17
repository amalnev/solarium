package ru.amalnev.solarium.library;

import lombok.Getter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.statements.IStatement;

public class NativeStatement implements IStatement
{
    @Getter
    private String functionName;

    private Library library;

    public NativeStatement(final String functionName, final Library library)
    {
        this.functionName = functionName;
        this.library = library;
    }

    @Override
    public ControlFlowInfluence execute(final ExecutionContext context) throws InterpreterException
    {
        final INativeFunction nativeFunction = library.getFunctionByName(functionName);
        nativeFunction.call(context);
        return ControlFlowInfluence.NO_INFLUENCE;
    }
}
