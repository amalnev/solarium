%{
import java.util.*;
import java.util.function.*;
import java.io.*;
import lombok.Getter;
import ru.amalnev.solarium.language.operators.*;
import ru.amalnev.solarium.language.expressions.*;
import ru.amalnev.solarium.language.statements.*;
import ru.amalnev.solarium.language.utils.*;
%}

%token SEMICOLON IDENTIFIER LE_OP GE_OP EQ_OP NE_OP AND_OP OR_OP
%token OPEN_SQUARE_BRACKET CLOSE_SQUARE_BRACKET
%token OPEN_BRACKET CLOSE_BRACKET COMMA
%token MINUS EXCLAMATION_MARK PLUS MUL DIV MODULO
%token LT GT EQ DOT NULL IF ELSE WHILE DO FOR
%token NUMERIC_LITERAL STRING_LITERAL TRUE FALSE
%token OPEN_CURLY_BRACKET CLOSE_CURLY_BRACKET
%token COLON CONTINUE BREAK RETURN FUNCTION

%start program

%%

program: { /*An empty program*/
	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody(null);
	this.entryPointFunction = entryPoint;
 }
 | statement_list {
 	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody((CompoundStatement)$1.obj);
	this.entryPointFunction = entryPoint;
 }
 ;

statement_list
 : statement {
 	CompoundStatement stmt = new CompoundStatement();
	stmt.getStatements().add((IStatement)$1.obj);
	$$ = new ParserVal(stmt);
 }
 | statement_list statement {
 	CompoundStatement stmt = (CompoundStatement)$1.obj;
	stmt.getStatements().add((IStatement)$2.obj);
	$$ = $1;
 }
 ;

statement
 : expression_statement {
 	$$ = $1;
 }
 | compound_statement {
 	$$ = $1;
 }
 | selection_statement {
 	$$ = $1;
 }
 | iteration_statement {
 	$$ = $1;
 }
 | jump_statement {
 	$$ = $1;
 }
 | function_definition {
 	$$ = $1;
 }
 ;

function_definition
 : FUNCTION IDENTIFIER OPEN_BRACKET identifier_list CLOSE_BRACKET compound_statement {
	String functionName = $2.sval;
	List<String> argumentNames = (List<String>) $4.obj;
	CompoundStatement body = (CompoundStatement) $6.obj;
	FunctionDefinition stmt = new FunctionDefinition();
	stmt.setFunctionName(functionName);
	stmt.setArgumentNames(argumentNames);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 | FUNCTION IDENTIFIER OPEN_BRACKET CLOSE_BRACKET compound_statement {
 	String functionName = $2.sval;
	CompoundStatement body = (CompoundStatement) $5.obj;
	FunctionDefinition stmt = new FunctionDefinition();
	stmt.setFunctionName(functionName);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 ;

identifier_list
 : IDENTIFIER {
 	List<String> identifierList = new CommaSeparatedList<>();
 	identifierList.add($1.sval);
 	$$ = new ParserVal(identifierList);
 }
 | identifier_list COMMA IDENTIFIER {
 	List<String> identifierList = (List<String>) $1.obj;
 	identifierList.add($3.sval);
 	$$ = $1;
 }
 ;

jump_statement
 : CONTINUE SEMICOLON {
 	$$ = new ParserVal(new ContinueStatement());
 }
 | BREAK SEMICOLON {
 	$$ = new ParserVal(new BreakStatement());
 }
 | RETURN SEMICOLON {
 	$$ = new ParserVal(new ReturnStatement());
 }
 | RETURN expression SEMICOLON {
 	$$ = new ParserVal(new ReturnStatement((IExpression) $2.obj));
 }
 ;

iteration_statement
 : WHILE OPEN_BRACKET expression CLOSE_BRACKET statement {
	IExpression condition = (IExpression) $3.obj;
	IStatement body = (IStatement) $5.obj;
	IterationStatement stmt = new WhileIterationStatement();
	stmt.setCondition(condition);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 | DO statement WHILE OPEN_BRACKET expression CLOSE_BRACKET SEMICOLON {
 	IExpression condition = (IExpression) $5.obj;
        IStatement body = (IStatement) $2.obj;
        IterationStatement stmt = new DoWhileIterationStatement();
	stmt.setCondition(condition);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 | FOR OPEN_BRACKET expression_statement expression_statement CLOSE_BRACKET statement {
 	IStatement initializer = (IStatement) $3.obj;
 	ExpressionStatement conditionStatement = (ExpressionStatement) $4.obj;
 	IStatement body = (IStatement) $6.obj;
 	ForIterationStatement stmt = new ForIterationStatement();
 	stmt.setCondition(conditionStatement.getExpression());
 	stmt.setInitializer(initializer);
 	stmt.setBody(body);
 	$$ = new ParserVal(stmt);
 }
 | FOR OPEN_BRACKET expression_statement expression_statement expression CLOSE_BRACKET statement {
 	IStatement initializer = (IStatement) $3.obj;
	ExpressionStatement conditionStatement = (ExpressionStatement) $4.obj;
	IExpression postIteration = (IExpression) $5.obj;
	IStatement body = (IStatement) $7.obj;
	ForIterationStatement stmt = new ForIterationStatement();
	stmt.setCondition(conditionStatement.getExpression());
	stmt.setInitializer(initializer);
	stmt.setPostIterationExpression(postIteration);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 | FOR OPEN_BRACKET IDENTIFIER COLON expression CLOSE_BRACKET statement {
	String elementName = $3.sval;
	IExpression collectionExpression = (IExpression) $5.obj;
	IStatement body = (IStatement) $7.obj;
	CollectionIterationStatement stmt = new CollectionIterationStatement();
	stmt.setElementName(elementName);
	stmt.setCollectionExpression(collectionExpression);
	stmt.setBody(body);
	$$ = new ParserVal(stmt);
 }
 ;

selection_statement
 : IF OPEN_BRACKET expression CLOSE_BRACKET statement {
 	IExpression condition = (IExpression) $3.obj;
 	IStatement positiveStatement = (IStatement) $5.obj;
 	SelectionStatement stmt = new SelectionStatement(condition, positiveStatement);
 	$$ = new ParserVal(stmt);
 }
 | IF OPEN_BRACKET expression CLOSE_BRACKET statement ELSE statement {
 	IExpression condition = (IExpression) $3.obj;
        IStatement positiveStatement = (IStatement) $5.obj;
        IStatement negativeStatement = (IStatement) $7.obj;
        SelectionStatement stmt = new SelectionStatement(condition, positiveStatement, negativeStatement);
        $$ = new ParserVal(stmt);
 }
 ;

compound_statement
 : OPEN_CURLY_BRACKET CLOSE_CURLY_BRACKET {
 	CompoundStatement stmt = new CompoundStatement();
 	$$ = new ParserVal(stmt);
 }
 | OPEN_CURLY_BRACKET statement_list CLOSE_CURLY_BRACKET {
 	$$ = $2;
 }
 ;

expression_statement
 : expression SEMICOLON {
 	IExpression expr = (IExpression) $1.obj;
 	ExpressionStatement stmt = new ExpressionStatement(expr);
 	$$ = new ParserVal(stmt);
 }
 ;

postfix_expression
 : primary_expression {
 	$$ = $1;
 }
 | postfix_expression OPEN_SQUARE_BRACKET expression CLOSE_SQUARE_BRACKET {
	IExpression arrayExpression = (IExpression) $1.obj;
	IExpression indexExpression = (IExpression) $3.obj;
	ArrayDereferenceExpression expr = new ArrayDereferenceExpression();
	expr.setArrayExpression(arrayExpression);
	expr.setIndexExpression(indexExpression);
	$$ = new ParserVal(expr);
 }
 | IDENTIFIER OPEN_BRACKET CLOSE_BRACKET {
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName($1.sval);
	$$ = new ParserVal(expr);
 }
 | IDENTIFIER OPEN_BRACKET argument_expression_list CLOSE_BRACKET {
 	List<IExpression> arguments = (List<IExpression>) $3.obj;
 	FunctionCallExpression expr = new FunctionCallExpression();
 	expr.setFunctionName($1.sval);
 	expr.setFunctionCallArguments(arguments);
 	$$ = new ParserVal(expr);
 }
 | postfix_expression DOT IDENTIFIER {
	IExpression sourceExpression = (IExpression) $1.obj;
	String fieldName = $3.sval;
	IExpression expr = new FieldAccessExpression(sourceExpression, fieldName);
	$$ = new ParserVal(expr);
 }
 | postfix_expression DOT IDENTIFIER OPEN_BRACKET argument_expression_list CLOSE_BRACKET {
	List<IExpression> arguments = (List<IExpression>) $5.obj;
	IExpression implicitArgument = (IExpression) $1.obj;
	arguments.add(0, implicitArgument);
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName($3.sval);
	expr.setFunctionCallArguments(arguments);
	$$ = new ParserVal(expr);
 }
 | postfix_expression DOT IDENTIFIER OPEN_BRACKET CLOSE_BRACKET {
 	List<IExpression> arguments = new CommaSeparatedList<>();
	IExpression implicitArgument = (IExpression) $1.obj;
	arguments.add(0, implicitArgument);
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName($3.sval);
	expr.setFunctionCallArguments(arguments);
	$$ = new ParserVal(expr);
 }
 ;

primary_expression
 : IDENTIFIER {
	VariableExpression expr = new VariableExpression($1.sval);
	$$ = new ParserVal(expr);
 }
 | NUMERIC_LITERAL {
	IntegerLiteralExpression expr = new IntegerLiteralExpression((Integer) $1.obj);
	$$ = new ParserVal(expr);
 }
 | STRING_LITERAL {
	StringLiteralExpression expr = new StringLiteralExpression($1.sval);
	$$ = new ParserVal(expr);
 }
 | TRUE {
	BooleanLiteralExpression expr = new BooleanLiteralExpression(true);
	$$ = new ParserVal(expr);
 }
 | FALSE {
	BooleanLiteralExpression expr = new BooleanLiteralExpression(false);
        $$ = new ParserVal(expr);
 }
 | NULL {
 	NullLiteralExpression expr = new NullLiteralExpression();
 	$$ = new ParserVal(expr);
 }
 | OPEN_BRACKET expression CLOSE_BRACKET {
 	$$ = $2;
 }
 | array_literal {
 	$$ = $1;
 }
 ;

array_literal
 : OPEN_SQUARE_BRACKET argument_expression_list CLOSE_SQUARE_BRACKET {
	List<IExpression> elements = (List<IExpression>) $2.obj;
	ArrayLiteralExpression expr = new ArrayLiteralExpression();
	expr.setElements(elements);
	$$ = new ParserVal(expr);
 }
 | OPEN_SQUARE_BRACKET  CLOSE_SQUARE_BRACKET {
	ArrayLiteralExpression expr = new ArrayLiteralExpression();
	$$ = new ParserVal(expr);
 }
 ;

argument_expression_list
 : assignment_expression {
 	List<IExpression> arguments = new CommaSeparatedList<>();
 	arguments.add((IExpression)$1.obj);
 	$$ = new ParserVal(arguments);
 }
 | argument_expression_list COMMA assignment_expression {
	List<IExpression> arguments = (List<IExpression>) $1.obj;
	arguments.add((IExpression) $3.obj);
	$$ = $1;
 }
 ;

unary_operator
 : MINUS {
 	IUnaryOperator op = new UnaryMinus();
 	$$ = new ParserVal(op);
 }
 | EXCLAMATION_MARK {
 	IUnaryOperator op = new Not();
 	$$ = new ParserVal(op);
 }
 ;

unary_expression
 : postfix_expression {
	$$ = $1;
 }
 | unary_operator postfix_expression {
 	IUnaryOperator op = (IUnaryOperator) $1.obj;
 	UnaryOperationExpression expr = new UnaryOperationExpression(op, (IExpression)$2.obj);
 	$$ = new ParserVal(expr);
 }
 ;

multiplicative_expression
 : unary_expression {
 	$$ = $1;
 }
 | multiplicative_expression MUL postfix_expression {
	$$ = constructBinaryOperation($1, new Mul(), $3);
 }
 | multiplicative_expression DIV postfix_expression {
 	$$ = constructBinaryOperation($1, new Div(), $3);
 }
 | multiplicative_expression MODULO postfix_expression {
 	$$ = constructBinaryOperation($1, new Modulo(), $3);
 }
 ;

additive_expression
 : multiplicative_expression {
 	$$ = $1;
 }
 | additive_expression PLUS multiplicative_expression {
 	$$ = constructBinaryOperation($1, new Plus(), $3);
 }
 | additive_expression MINUS multiplicative_expression {
 	$$ = constructBinaryOperation($1, new Minus(), $3);
 }
 ;

relational_expression
 : additive_expression {
 	$$ = $1;
 }
 | relational_expression LT additive_expression {
 	$$ = constructBinaryOperation($1, new Lt(), $3);
 }
 | relational_expression GT additive_expression {
 	$$ = constructBinaryOperation($1, new Gt(), $3);
 }
 | relational_expression LE_OP additive_expression {
 	$$ = constructBinaryOperation($1, new Le(), $3);
 }
 | relational_expression GE_OP additive_expression {
 	$$ = constructBinaryOperation($1, new Ge(), $3);
 }
 ;

equality_expression
 : relational_expression {
 	$$ = $1;
 }
 | equality_expression EQ_OP relational_expression {
 	$$ = constructBinaryOperation($1, new Eq(), $3);
 }
 | equality_expression NE_OP relational_expression {
 	$$ = constructBinaryOperation($1, new Neq(), $3);
 }
 ;

logical_and_expression
 : equality_expression {
 	$$ = $1;
 }
 | logical_and_expression AND_OP equality_expression {
 	$$ = constructBinaryOperation($1, new And(), $3);
 }
 ;

logical_or_expression
 : logical_and_expression {
 	$$ = $1;
 }
 | logical_or_expression OR_OP logical_and_expression {
 	$$ = constructBinaryOperation($1, new Or(), $3);
 }
 ;

assignment_expression
 : logical_or_expression {
 	$$ = $1;
 }
 | postfix_expression EQ assignment_expression {
	IExpression leftOperand = (IExpression) $1.obj;
	IExpression rightOperand = (IExpression) $3.obj;
	AssignmentExpression expr = new AssignmentExpression(leftOperand, rightOperand);
	$$ = new ParserVal(expr);
 }
 ;

expression
 : assignment_expression {
 	$$ = $1;
 }
 ;

%%

private FunctionDefinition entryPointFunction;

@Getter
private Lexer lexer;

private void yyerror(final String s) throws ParserException
{
    throw new ParserException(s, this);
}

private int yylex() throws IOException
{
    int token = lexer.yylex();
    if(token == Lexer.YYEOF) return 0;
    return token;
}

private ParserVal constructBinaryOperation(ParserVal left, IBinaryOperator op, ParserVal right)
{
    IExpression leftOperand = (IExpression) left.obj;
    IExpression rightOperand = (IExpression) right.obj;
    IExpression expr = new BinaryOperationExpression(leftOperand, op, rightOperand);
    return new ParserVal(expr);
}

public FunctionDefinition parse(final Reader inputReader) throws ParserException, IOException
{
    lexer = new Lexer(inputReader, this);
    yyparse();
    return entryPointFunction;
}