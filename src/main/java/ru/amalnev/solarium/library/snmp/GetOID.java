package ru.amalnev.solarium.library.snmp;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;

@FunctionName("getOID")
@FunctionArguments({"oid", "hostIp", "community", "protocolVersion"})
public class GetOID extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String oid = getScalarArgument(context, "oid");
            final String hostIp = getScalarArgument(context, "hostIp");
            final String community = getScalarArgument(context, "community");
            final String protocolVersion = getScalarArgument(context, "protocolVersion");

            final SimpleSnmpClient snmpClient = new SimpleSnmpClient("udp:" + hostIp + "/161", community, protocolVersion);
            final String result = snmpClient.getAsString(oid);
            snmpClient.stop();
            setReturnValue(context, result);
        }
        catch (IOException e)
        {
            throw new InterpreterException(e);
        }
    }
}
