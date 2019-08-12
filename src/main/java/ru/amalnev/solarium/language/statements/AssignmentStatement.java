package ru.amalnev.solarium.language.statements;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.language.expressions.IExpression;

@Getter
@Setter
public abstract class AssignmentStatement extends AbstractStatement
{
    private IExpression rightHandOperand;

    private IExpression leftHandOperand;
}
