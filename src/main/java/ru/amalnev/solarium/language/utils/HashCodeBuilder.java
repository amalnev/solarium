package ru.amalnev.solarium.language.utils;

public class HashCodeBuilder
{
    private StringBuilder builder = new StringBuilder();

    public HashCodeBuilder append(Object value)
    {
        if (value == null)
            builder.append("null");
        else
            builder.append(value.toString());

        return this;
    }

    @Override
    public int hashCode()
    {
        return builder.toString().hashCode();
    }
}
