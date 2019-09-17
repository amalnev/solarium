package ru.amalnev.solarium.language.utils;

import java.util.ArrayList;

public class CommaSeparatedList<T> extends ArrayList<T>
{
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size(); i++)
        {
            builder.append(get(i).toString());
            if (i != size() - 1)
            {
                builder.append(", ");
            }
        }

        return builder.toString();
    }
}
