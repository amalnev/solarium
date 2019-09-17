package ru.amalnev.solarium.language.operators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class BasicOperatorImpl<T, R>
{
    @Getter
    protected Class<T> operandClass;

    @Getter
    protected Class<R> resultClass;
}
