package ru.amalnev.solarium.library.common;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("println")
@FunctionArguments("what")
public class Println extends Print
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        super.call(context);
        System.out.println();
    }
}
