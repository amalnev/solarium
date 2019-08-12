%{
import java.util.*;
import java.io.IOException;
import lombok.Getter;
import ru.amalnev.solarium.language.operators.*;
import ru.amalnev.solarium.language.expressions.*;
import ru.amalnev.solarium.language.statements.*;
%}

%token NUMERIC_LITERAL
%token STRING_LITERAL
%token EQ
%token WORD
%token SEMICOLON
%token OPEN_BRACKET
%token CLOSE_BRACKET
%token COMMA
%token FUNCTION_KEYWORD
%token OPEN_CURLY_BRACKET
%token CLOSE_CURLY_BRACKET
%token TRUE_KEYWORD
%token FALSE_KEYWORD
%token IF_KEYWORD
%token ELSE_KEYWORD
%token GT LT DBLEQ AND OR
%token FOR_KEYWORD
%token COLON
%token OPEN_SQUARE_BRACKET
%token CLOSE_SQUARE_BRACKET
%token RETURN_KEYWORD
%token BREAK_KEYWORD
%token PLUS MINUS MUL DIV
%token DOT

%left GT LT DBLEQ
%left PLUS MINUS
%left MUL DIV
%left AND
%left OR
%left OPEN_BRACKET CLOSE_BRACKET

%%
input: statement_list  {
	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody((CodeBlock)$1.obj);
	this.entryPointFunction = entryPoint;
 }
 ;

statement_list:
 | statement {
 	CodeBlock codeBlock = new CodeBlock();
	codeBlock.getStatements().add((IStatement)$1.obj);
 	$$ = new ParserVal(codeBlock);
 }
 | statement_list statement {
 	CodeBlock codeBlock = (CodeBlock)$1.obj;
  	codeBlock.getStatements().add((IStatement)$2.obj);
  	$$ = $1;
 }
 ;

statement: statement_body SEMICOLON {
	$$ = $1;
 }
 ;

statement_body: assignment {
	$$ = $1;
 }
 | expression {
	$$ = new ParserVal(new ExpressionStatement((IExpression)$1.obj));
 }
 | function_definition {
  	$$ = $1;
 }
 | conditional_statement {
 	$$ = $1;
 }
 | loop_statement {
	$$ = $1;
 }
 | return_statement {
	$$ = $1;
 }
 | break_statement {
 	$$ = $1;
 }
 ;

break_statement: BREAK_KEYWORD {
	BreakStatement stmt = new BreakStatement();
	$$ = new ParserVal(stmt);
 }
 ;

return_statement: RETURN_KEYWORD expression {
	ReturnStatement stmt = new ReturnStatement((IExpression)$2.obj);
	$$ = new ParserVal(stmt);
 }
 | RETURN_KEYWORD {
	ReturnStatement stmt = new ReturnStatement();
	$$ = new ParserVal(stmt);
 }
 ;

loop_statement: for_loop {
	$$ = $1;
 }
 ;

for_loop: FOR_KEYWORD OPEN_BRACKET varname COLON expression CLOSE_BRACKET code_block {
	Variable var = (Variable)$3.obj;
	IExpression collection = (IExpression)$5.obj;
	CodeBlock body = (CodeBlock)$7.obj;

	LoopStatement stmt = new LoopStatement();
	stmt.setLoopVariable(var);
	stmt.setLoopCollection(collection);
	stmt.setLoopBody(body);
	$$ = new ParserVal(stmt);
 }
 ;

code_block: OPEN_CURLY_BRACKET statement_list CLOSE_CURLY_BRACKET {
	$$ = $2;
 }
 ;

conditional_statement: if_clause {
	$$ = $1;
 }
 | if_clause else_clause {
	ConditionalStatement conditionalStatement = (ConditionalStatement)$1.obj;
	conditionalStatement.setNegativeStatements((CodeBlock)$2.obj);
	$$ = $1;
 }
 ;

if_clause: IF_KEYWORD OPEN_BRACKET expression CLOSE_BRACKET code_block {
	ConditionalStatement conditionalStatement = new ConditionalStatement();
	conditionalStatement.setCondition((IExpression)$3.obj);
	conditionalStatement.setPositiveStatements((CodeBlock)$5.obj);
	$$ = new ParserVal(conditionalStatement);
 }
 ;

else_clause: ELSE_KEYWORD code_block {
	$$ = new ParserVal((CodeBlock)$2.obj);
 }
 ;

function_definition: FUNCTION_KEYWORD WORD OPEN_BRACKET argument_name_list CLOSE_BRACKET code_block {
	FunctionDefinition functionDefinition = new FunctionDefinition();
	functionDefinition.setBody((CodeBlock)$6.obj);
	functionDefinition.setFunctionName($2.sval);
	functionDefinition.setArgumentNames((List<String>)$4.obj);
	$$ = new ParserVal(functionDefinition);
 }
 ;

argument_name_list: {
	$$ = new ParserVal(new LinkedList<String>());
 }
 | argument_name_list COMMA WORD {
 	List<String> argumentList = (List<String>)$1.obj;
        argumentList.add($3.sval);
        $$ = $1;
 }
 | WORD {
 	List<String> argumentList = new LinkedList<>();
 	argumentList.add($1.sval);
 	$$ = new ParserVal(argumentList);
 }
 ;

assignment: varname EQ expression {
	AssignmentStatement stmt = new VariableAssignmentStatement();
	stmt.setLeftHandOperand((Variable)$1.obj);
	stmt.setRightHandOperand((IExpression)$3.obj);
	$$ = new ParserVal(stmt);
 }
 | array_dereference EQ expression {
	AssignmentStatement stmt = new ArrayElementAssignmentStatement();
	stmt.setLeftHandOperand((IExpression)$1.obj);
	stmt.setRightHandOperand((IExpression)$3.obj);
	$$ = new ParserVal(stmt);
 }
 ;

varname: WORD {
	$$ = new ParserVal(new Variable($1.sval));
 }
 ;

expression: literal {
	$$ = $1;
 }
 | function_call {
	$$ = $1;
 }
 | varname {
	$$ = $1;
 }
 | operation {
	$$ = $1;
 }
 | array_dereference {
	$$ = $1;
 }
 ;

array_dereference: expression OPEN_SQUARE_BRACKET expression CLOSE_SQUARE_BRACKET {
	ArrayDereferenceExpression expr = new ArrayDereferenceExpression();
	expr.setArrayExpression((IExpression)$1.obj);
	expr.setIndexExpression((IExpression)$3.obj);
	$$ = new ParserVal(expr);
 }
 ;

operation: binary_operation {
	$$ = $1;
 }
 | unary_operation {
 	$$ = $1;
 }
 ;

unary_operation: unary_operator expression {
	UnaryOperationExpression expr = new UnaryOperationExpression();
	expr.setOperand((IExpression)$2.obj);
	expr.setOperator((IUnaryOperator)$1.obj);
	$$ = new ParserVal(expr);
 }
 ;

unary_operator : MINUS {
	$$ = new ParserVal(new UnaryMinus());
 }
 ;

binary_operation: expression PLUS expression {
	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Plus());
 }
 | expression MINUS expression {
 	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Minus());
 }
 | expression MUL expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Mul());
 }
 | expression DIV expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Div());
 }
 | expression GT expression {
 	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Gt());
 }
 | expression LT expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Lt());
 }
 | expression DBLEQ expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Eq());
 }
 | expression AND expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new And());
 }
 | expression OR expression {
  	$$ = constructBinaryOperation((IExpression)$1.obj, (IExpression)$3.obj, new Or());
 }
 | OPEN_BRACKET binary_operation CLOSE_BRACKET {
  	$$ = $2;
 }
 ;

literal: NUMERIC_LITERAL {
	$$ = new ParserVal(new IntegerLiteral($1.ival));
 }
 | STRING_LITERAL {
 	$$ = new ParserVal(new StringLiteral($1.sval));
 }
 | TRUE_KEYWORD {
 	$$ = new ParserVal(new BooleanLiteral(true));
 }
 | FALSE_KEYWORD {
  	$$ = new ParserVal(new BooleanLiteral(false));
 }
 | array_literal {
	$$ = $1;
 }
 ;

array_literal: OPEN_SQUARE_BRACKET expression_list CLOSE_SQUARE_BRACKET {
	ArrayLiteral literal = new ArrayLiteral();
	literal.setElements((List<IExpression>)$2.obj);
	$$ = new ParserVal(literal);
 }
 ;

function_call: function_name OPEN_BRACKET expression_list CLOSE_BRACKET {
	FunctionCallExpression functionCall = new FunctionCallExpression();
	functionCall.setFunctionName($1.sval);
	functionCall.setFunctionCallArguments((List<IExpression>)$3.obj);
	$$ = new ParserVal(functionCall);
 }
 | expression DOT function_call {
	FunctionCallExpression expr = (FunctionCallExpression)$3.obj;
	expr.getFunctionCallArguments().add(0,(IExpression)$1.obj);
	$$ = $3;
 }
 ;

function_name: WORD {
	$$ = $1;
 }
 ;

expression_list: {
	List<IExpression> argumentList = new LinkedList<>();
        $$ = new ParserVal(argumentList);
 }
 | expression_list COMMA expression {
 	List<IExpression> argumentList = (List<IExpression>)$1.obj;
 	IExpression expression = (IExpression)$3.obj;
 	argumentList.add(expression);
 	$$ = $1;
 }
 | expression {
	List<IExpression> argumentList = new LinkedList<>();
	IExpression expression = (IExpression)$1.obj;
	argumentList.add(expression);
	$$ = new ParserVal(argumentList);
 }
 ;
%%

private FunctionDefinition entryPointFunction;

@Getter
private Lexer lexer;

private static ParserVal constructBinaryOperation(
	IExpression leftOperand,
	IExpression rightOperand,
	IBinaryOperator operator)
{
	BinaryOperationExpression expr = new BinaryOperationExpression();
	expr.setLeftOperand(leftOperand);
	expr.setRightOperand(rightOperand);
	expr.setOperator(operator);
	return new ParserVal(expr);
}

private void yyerror(final String s) throws ParserException
{
    throw new ParserException(s, this);
}

private int yylex() throws IOException
{
    return lexer.yylex();
}

public FunctionDefinition parseFromString(final String inputString) throws ParserException, IOException
{
    lexer = new Lexer(this, inputString);
    yyparse();
    return entryPointFunction;
}

