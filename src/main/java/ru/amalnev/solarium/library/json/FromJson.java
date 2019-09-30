package ru.amalnev.solarium.library.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@FunctionName("fromJSON")
@FunctionArguments("jsonString")
public class FromJson extends AbstractNativeFunction
{
    private IValue convert(JsonNode node) throws InterpreterException
    {
        final Iterator<Map.Entry<String, JsonNode>> nodeIterator = node.fields();
        final IValue result = new Value();
        while (nodeIterator.hasNext())
        {
            final Map.Entry<String, JsonNode> field = nodeIterator.next();
            final String fieldName = field.getKey();
            final JsonNode fieldNode = field.getValue();
            if (fieldName.equals("."))
            {
                if (fieldNode.getNodeType() == JsonNodeType.ARRAY)
                {
                    result.convertToArray();
                    final Iterator<JsonNode> elementIterator = fieldNode.elements();
                    while (elementIterator.hasNext())
                    {
                        final JsonNode elementNode = elementIterator.next();
                        result.addArrayElement(convert(elementNode));
                    }
                }
                else if (fieldNode.isValueNode())
                {
                    final String nodeValue = fieldNode.asText();
                    if (nodeValue.equals("null"))
                    {
                        result.setScalarValue(null);
                    }
                    else if (fieldNode.isInt())
                    {
                        result.setScalarValue(fieldNode.asInt());
                    }
                    else
                    {
                        result.setScalarValue(nodeValue);
                    }
                }
                else
                {
                    throw new InterpreterException();
                }
            }
            else
            {
                result.setField(fieldName, convert(fieldNode));
            }
        }

        return result;
    }

    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String json = getScalarArgument(context, "jsonString");
            final JsonNode rootNode = objectMapper.readTree(json);
            context.setReturnValue(convert(rootNode));
        }
        catch (IOException e)
        {
            throw new InterpreterException();
        }
    }
}
