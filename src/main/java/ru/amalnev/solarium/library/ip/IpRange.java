package ru.amalnev.solarium.library.ip;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.util.Iterator;

@FunctionName("ipRange")
@FunctionArguments("ipRangeString")
public class IpRange extends AbstractNativeFunction
{
    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final String ipRangeString = getScalarArgument(context, "ipRangeString");
            final IPAddress rangeAddress = new IPAddressString(ipRangeString).toAddress();
            final Iterator<? extends IPAddress> rangeIterator = rangeAddress.iterator();
            final IValue result = new Value().convertToArray();
            while (rangeIterator.hasNext())
            {
                final IPAddress ipAddress = rangeIterator.next().withoutPrefixLength();
                result.addArrayElement(new Value(ipAddress.toCanonicalString()));
            }

            context.setReturnValue(result);
        }
        catch (AddressStringException e)
        {
            throw new InterpreterException(e);
        }
    }
}
