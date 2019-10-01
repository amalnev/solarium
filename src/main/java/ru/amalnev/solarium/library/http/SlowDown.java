package ru.amalnev.solarium.library.http;

import org.apache.commons.lang3.ClassUtils;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.lang.reflect.Proxy;
import java.util.List;

@FunctionName("slowDown")
@FunctionArguments({"target", "lag"})
public class SlowDown extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final Object target = getScalarArgument(context, "target");
        final Integer lag = getScalarArgument(context, "lag");
        final List<Class<?>> implementedInterfacesList = ClassUtils.getAllInterfaces(target.getClass());
        final Class[] implementedInterfacesArray = new Class[implementedInterfacesList.size()];
        final Object proxyInstance = Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                implementedInterfacesList.toArray(implementedInterfacesArray),
                (proxy, method, methodArgs) ->
                {
                    Thread.sleep(lag);
                    try
                    {
                        return method.invoke(target, methodArgs);
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                });
        setReturnValue(context, proxyInstance);
    }
}
