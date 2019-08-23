package ru.amalnev.solarium.interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RValue implements IRValue
{
    @Getter
    private Object value;
}
