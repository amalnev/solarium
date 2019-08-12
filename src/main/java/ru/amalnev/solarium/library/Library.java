package ru.amalnev.solarium.library;

import org.reflections.Reflections;
import ru.amalnev.solarium.language.statements.CodeBlock;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.*;

public class Library
{
    private final Map<String, AbstractNativeFunction> nativeFunctions = new HashMap<>();

    public Library()
    {
        final Reflections reflections = new Reflections("ru.amalnev.solarium");
        reflections.getSubTypesOf(AbstractNativeFunction.class).forEach(nativeFunctionClass -> {
            if (nativeFunctionClass.isAnnotationPresent(FunctionName.class))
            {
                try
                {
                    final String nativeFunctionName = nativeFunctionClass.getAnnotation(FunctionName.class).value();
                    final AbstractNativeFunction functionDefinition = nativeFunctionClass.newInstance();
                    functionDefinition.setFunctionName(nativeFunctionName);
                    if (nativeFunctionClass.isAnnotationPresent(FunctionArguments.class))
                    {
                        final List<String> argumentNames = new LinkedList<>(
                                Arrays.asList(nativeFunctionClass.getAnnotation(FunctionArguments.class).value()));
                        functionDefinition.setArgumentNames(argumentNames);
                    }

                    final CodeBlock body = new CodeBlock();
                    body.getStatements().add(new NativeStatement(nativeFunctionName, this));

                    functionDefinition.setBody(body);

                    nativeFunctions.put(nativeFunctionName, functionDefinition);
                }
                catch (IllegalAccessException | InstantiationException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<FunctionDefinition> getNativeFunctions()
    {
        return new LinkedList<>(nativeFunctions.values());
    }

    public INativeFunction getFunctionByName(final String name)
    {
        return nativeFunctions.get(name);
    }
}
