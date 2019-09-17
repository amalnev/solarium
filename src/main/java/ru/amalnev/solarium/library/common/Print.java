package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("print")
@FunctionArguments("what")
public class Print extends AbstractNativeFunction
{
    @Override
    public void call(final ExecutionContext context) throws InterpreterException
    {
        final IValue what = context.findVariable("what");
        System.out.println(what.toString());
    }
}
