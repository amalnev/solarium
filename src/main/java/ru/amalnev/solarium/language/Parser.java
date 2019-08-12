//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package ru.amalnev.solarium.language;



//#line 2 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
import java.util.*;
import java.io.IOException;
import lombok.Getter;
import ru.amalnev.solarium.language.operators.*;
import ru.amalnev.solarium.language.expressions.*;
import ru.amalnev.solarium.language.statements.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUMERIC_LITERAL=257;
public final static short STRING_LITERAL=258;
public final static short EQ=259;
public final static short WORD=260;
public final static short SEMICOLON=261;
public final static short OPEN_BRACKET=262;
public final static short CLOSE_BRACKET=263;
public final static short COMMA=264;
public final static short FUNCTION_KEYWORD=265;
public final static short OPEN_CURLY_BRACKET=266;
public final static short CLOSE_CURLY_BRACKET=267;
public final static short TRUE_KEYWORD=268;
public final static short FALSE_KEYWORD=269;
public final static short IF_KEYWORD=270;
public final static short ELSE_KEYWORD=271;
public final static short GT=272;
public final static short LT=273;
public final static short DBLEQ=274;
public final static short AND=275;
public final static short OR=276;
public final static short NOT=277;
public final static short FOR_KEYWORD=278;
public final static short COLON=279;
public final static short OPEN_SQUARE_BRACKET=280;
public final static short CLOSE_SQUARE_BRACKET=281;
public final static short RETURN_KEYWORD=282;
public final static short BREAK_KEYWORD=283;
public final static short PLUS=284;
public final static short MINUS=285;
public final static short MUL=286;
public final static short DIV=287;
public final static short DOT=288;
public final static short NULL_KEYWORD=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    3,    3,    3,    3,    3,
    3,    3,   10,    9,    9,    8,   11,   13,    7,    7,
   14,   15,    6,   16,   16,   16,    4,    4,   12,    5,
    5,    5,    5,    5,   17,   20,   20,   22,   23,   23,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   18,   18,   18,   18,   18,   18,   24,   19,   19,   26,
   25,   25,   25,
};
final static short yylen[] = {                            2,
    1,    0,    1,    2,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    1,    7,    3,    1,    2,
    5,    2,    6,    0,    3,    1,    3,    3,    1,    1,
    1,    1,    1,    1,    4,    1,    1,    2,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    1,    1,    1,    1,    1,    1,    3,    4,    3,    1,
    0,    3,    1,
};
final static short yydefred[] = {                         0,
   51,   52,    0,    0,    0,   53,   54,    0,   40,    0,
    0,    0,   13,   39,   56,    0,    0,    3,    0,    6,
    0,    8,    9,   10,   11,   12,   16,    0,    0,    0,
   30,   31,   33,   36,   37,    0,   55,    0,    0,   32,
   34,    0,    0,    0,    0,    0,    0,    0,    4,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    0,    0,   50,    0,    0,
   29,    0,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   22,    0,    0,
   26,    0,    0,    0,    0,   35,    0,   58,    0,    0,
   21,    0,   18,   23,   25,    0,   17,
};
final static short yydgoto[] = {                         16,
   17,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   27,   40,   88,   29,   64,   92,   41,   31,   32,   33,
   34,   35,   36,   37,   47,   38,
};
final static short yysindex[] = {                      -198,
    0,    0,    0, -247, -254,    0,    0, -242,    0, -225,
 -247, -247,    0,    0,    0,    0, -198,    0, -222,    0,
   97,    0,    0,    0,    0,    0,    0, -218, -227, -194,
    0,    0,    0,    0,    0, -247,    0, -196,   97,    0,
    0, -195, -189, -247, -185,   97, -252,   97,    0,    0,
 -247, -247, -247, -247, -247, -247, -247, -247, -247, -247,
 -247, -247, -188,    0, -247,   97, -247,    0, -179,    8,
    0, -193, -247,    0,  128,  128,  128, -169, -264,   80,
  131,  131, -271, -271,    0,   97, -198,    0,   97, -154,
    0, -132, -188, -247,   97,    0, -165,    0, -188, -164,
    0,   26,    0,    0,    0, -188,    0,
};
final static short yyrindex[] = {                       101,
    0,    0, -136,    0,    0,    0,    0,    0,    0,    0,
 -250, -155,    0,    0,    0,    0,  116,    0,    0,    0,
 -139,    0,    0,    0,    0,    0,    0,  -91, -126,  -74,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  114,    0,    0,    0, -236,    0, -118,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -187, -130,    0, -122,    0,
    0,    0,    0,    0, -238,   63,   77,  -40,  -56,    0,
   44,   58,  -24,   -8, -108, -115, -113,    0, -104,    0,
    0,    0,    0,    0, -134,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   71,  -14,    0,    0,   -4,    0,    0,    0,    0,    0,
    0,    1,   15,    0,    0,    0,    2,    0,   98,    0,
  156,    0,    0,    0,   94,    0,
};
final static int YYTABLESIZE=419;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   28,   30,   49,   54,   55,   43,   46,   48,   56,    1,
    2,   73,    3,   61,    4,   56,   61,   28,   30,   44,
    6,    7,   45,   61,   45,   45,   63,   63,   74,    9,
   61,   66,   11,   45,   45,   45,   45,   14,   50,   70,
   62,   15,   45,   63,   63,   72,   75,   76,   77,   78,
   79,   80,   81,   82,   83,   84,   39,   86,    1,    2,
   89,    3,   46,    4,   65,   67,    5,   68,   95,    6,
    7,    8,   69,   38,   71,   38,   38,   87,    9,   10,
   91,   11,   49,   12,   13,   94,   14,   28,   30,  102,
   15,    1,    2,   38,    3,  105,    4,   28,   30,    5,
    2,  103,    6,    7,    8,   15,   55,  101,   98,   73,
   56,    9,   10,  104,   11,    1,   12,   13,   61,   14,
  107,    7,   29,   15,   29,   60,   29,   29,   62,   62,
   99,  100,   61,   61,   19,   29,   29,   29,   29,   29,
   24,   24,   14,   29,   29,   27,   62,   29,   29,   29,
   29,   29,   59,    2,   59,   59,   28,   97,   85,   42,
   90,    0,    0,   31,   31,   31,   31,   31,    0,   32,
    0,   31,   59,    0,    0,   31,   31,   31,   31,   31,
   32,   32,   32,   32,   32,    0,   34,    0,   32,    0,
    0,    0,   32,   32,   32,   32,   32,   34,   34,   34,
   34,   34,    0,    0,   49,   34,   49,   49,    0,   34,
   34,   34,   34,   34,    0,   49,   49,   49,   49,   49,
   48,    0,   48,   48,   49,    0,    0,   49,   49,   49,
   49,   48,   48,   48,   48,    0,   43,    0,   43,   43,
   48,    0,    0,   48,   48,   48,   48,   43,   43,   43,
    0,    0,   44,    0,   44,   44,   43,    0,    0,   43,
   43,   43,   43,   44,   44,   44,    0,    0,    0,    0,
   93,    0,   44,    0,    0,   44,   44,   44,   44,   51,
   52,   53,   54,   55,    0,    0,    0,   56,  106,    0,
    0,   57,   58,   59,   60,   61,    0,   51,   52,   53,
   54,   55,    0,    0,   41,   56,   41,   41,    0,   57,
   58,   59,   60,   61,    0,   41,   41,   41,   42,    0,
   42,   42,    0,   46,   41,   46,   46,   41,   41,   42,
   42,   42,    0,    0,   46,   46,   46,   47,   42,   47,
   47,   42,   42,   46,    0,    0,    0,    0,   47,   47,
   47,   51,   52,   53,   54,   55,    0,   47,    0,   56,
   96,    0,    0,   57,   58,   59,   60,   61,   51,   52,
   53,   54,   55,    0,    0,    0,   56,    0,    0,    0,
   57,   58,   59,   60,   61,   36,   36,   36,   36,   36,
    0,    0,    0,   36,    0,    0,    0,   36,   36,   36,
   36,   36,   54,   55,    0,   54,   55,   56,    0,    0,
   56,   57,   58,   59,   60,   61,   59,   60,   61,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    0,    0,   17,  275,  276,  260,   11,   12,  280,  257,
  258,  264,  260,  264,  262,  280,  288,   17,   17,  262,
  268,  269,  261,  288,  263,  264,  263,  264,  281,  277,
  281,   36,  280,  272,  273,  274,  262,  285,  261,   44,
  259,  289,  281,  271,  281,   45,   51,   52,   53,   54,
   55,   56,   57,   58,   59,   60,   61,   62,  257,  258,
   65,  260,   67,  262,  259,  262,  265,  263,   73,  268,
  269,  270,  262,  261,  260,  263,  264,  266,  277,  278,
  260,  280,   97,  282,  283,  279,  285,   87,   87,   94,
  289,  257,  258,  281,  260,  260,  262,   97,   97,  265,
    0,  267,  268,  269,  270,  261,  276,   93,  263,  264,
  280,  277,  278,   99,  280,    0,  282,  283,  288,  285,
  106,  261,  259,  289,  261,  262,  263,  264,  263,  264,
  263,  264,  263,  264,  261,  272,  273,  274,  275,  276,
  263,  264,  261,  280,  281,  261,  281,  284,  285,  286,
  287,  288,  261,  267,  263,  264,  261,   87,   61,    4,
   67,   -1,   -1,  272,  273,  274,  275,  276,   -1,  261,
   -1,  280,  281,   -1,   -1,  284,  285,  286,  287,  288,
  272,  273,  274,  275,  276,   -1,  261,   -1,  280,   -1,
   -1,   -1,  284,  285,  286,  287,  288,  272,  273,  274,
  275,  276,   -1,   -1,  261,  280,  263,  264,   -1,  284,
  285,  286,  287,  288,   -1,  272,  273,  274,  275,  276,
  261,   -1,  263,  264,  281,   -1,   -1,  284,  285,  286,
  287,  272,  273,  274,  275,   -1,  261,   -1,  263,  264,
  281,   -1,   -1,  284,  285,  286,  287,  272,  273,  274,
   -1,   -1,  261,   -1,  263,  264,  281,   -1,   -1,  284,
  285,  286,  287,  272,  273,  274,   -1,   -1,   -1,   -1,
  263,   -1,  281,   -1,   -1,  284,  285,  286,  287,  272,
  273,  274,  275,  276,   -1,   -1,   -1,  280,  263,   -1,
   -1,  284,  285,  286,  287,  288,   -1,  272,  273,  274,
  275,  276,   -1,   -1,  261,  280,  263,  264,   -1,  284,
  285,  286,  287,  288,   -1,  272,  273,  274,  261,   -1,
  263,  264,   -1,  261,  281,  263,  264,  284,  285,  272,
  273,  274,   -1,   -1,  272,  273,  274,  261,  281,  263,
  264,  284,  285,  281,   -1,   -1,   -1,   -1,  272,  273,
  274,  272,  273,  274,  275,  276,   -1,  281,   -1,  280,
  281,   -1,   -1,  284,  285,  286,  287,  288,  272,  273,
  274,  275,  276,   -1,   -1,   -1,  280,   -1,   -1,   -1,
  284,  285,  286,  287,  288,  272,  273,  274,  275,  276,
   -1,   -1,   -1,  280,   -1,   -1,   -1,  284,  285,  286,
  287,  288,  275,  276,   -1,  275,  276,  280,   -1,   -1,
  280,  284,  285,  286,  287,  288,  286,  287,  288,
};
}
final static short YYFINAL=16;
final static short YYMAXTOKEN=289;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"NUMERIC_LITERAL","STRING_LITERAL","EQ","WORD","SEMICOLON",
"OPEN_BRACKET","CLOSE_BRACKET","COMMA","FUNCTION_KEYWORD","OPEN_CURLY_BRACKET",
"CLOSE_CURLY_BRACKET","TRUE_KEYWORD","FALSE_KEYWORD","IF_KEYWORD",
"ELSE_KEYWORD","GT","LT","DBLEQ","AND","OR","NOT","FOR_KEYWORD","COLON",
"OPEN_SQUARE_BRACKET","CLOSE_SQUARE_BRACKET","RETURN_KEYWORD","BREAK_KEYWORD",
"PLUS","MINUS","MUL","DIV","DOT","NULL_KEYWORD",
};
final static String yyrule[] = {
"$accept : input",
"input : statement_list",
"statement_list :",
"statement_list : statement",
"statement_list : statement_list statement",
"statement : statement_body SEMICOLON",
"statement_body : assignment",
"statement_body : expression",
"statement_body : function_definition",
"statement_body : conditional_statement",
"statement_body : loop_statement",
"statement_body : return_statement",
"statement_body : break_statement",
"break_statement : BREAK_KEYWORD",
"return_statement : RETURN_KEYWORD expression",
"return_statement : RETURN_KEYWORD",
"loop_statement : for_loop",
"for_loop : FOR_KEYWORD OPEN_BRACKET varname COLON expression CLOSE_BRACKET code_block",
"code_block : OPEN_CURLY_BRACKET statement_list CLOSE_CURLY_BRACKET",
"conditional_statement : if_clause",
"conditional_statement : if_clause else_clause",
"if_clause : IF_KEYWORD OPEN_BRACKET expression CLOSE_BRACKET code_block",
"else_clause : ELSE_KEYWORD code_block",
"function_definition : FUNCTION_KEYWORD WORD OPEN_BRACKET argument_name_list CLOSE_BRACKET code_block",
"argument_name_list :",
"argument_name_list : argument_name_list COMMA WORD",
"argument_name_list : WORD",
"assignment : varname EQ expression",
"assignment : array_dereference EQ expression",
"varname : WORD",
"expression : literal",
"expression : function_call",
"expression : varname",
"expression : operation",
"expression : array_dereference",
"array_dereference : expression OPEN_SQUARE_BRACKET expression CLOSE_SQUARE_BRACKET",
"operation : binary_operation",
"operation : unary_operation",
"unary_operation : unary_operator expression",
"unary_operator : MINUS",
"unary_operator : NOT",
"binary_operation : expression PLUS expression",
"binary_operation : expression MINUS expression",
"binary_operation : expression MUL expression",
"binary_operation : expression DIV expression",
"binary_operation : expression GT expression",
"binary_operation : expression LT expression",
"binary_operation : expression DBLEQ expression",
"binary_operation : expression AND expression",
"binary_operation : expression OR expression",
"binary_operation : OPEN_BRACKET binary_operation CLOSE_BRACKET",
"literal : NUMERIC_LITERAL",
"literal : STRING_LITERAL",
"literal : TRUE_KEYWORD",
"literal : FALSE_KEYWORD",
"literal : array_literal",
"literal : NULL_KEYWORD",
"array_literal : OPEN_SQUARE_BRACKET expression_list CLOSE_SQUARE_BRACKET",
"function_call : function_name OPEN_BRACKET expression_list CLOSE_BRACKET",
"function_call : expression DOT function_call",
"function_name : WORD",
"expression_list :",
"expression_list : expression_list COMMA expression",
"expression_list : expression",
};

//#line 342 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"

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

//#line 420 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws ParserException,IOException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 45 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody((CodeBlock)val_peek(0).obj);
	this.entryPointFunction = entryPoint;
 }
break;
case 3:
//#line 53 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	CodeBlock codeBlock = new CodeBlock();
	codeBlock.getStatements().add((IStatement)val_peek(0).obj);
 	yyval = new ParserVal(codeBlock);
 }
break;
case 4:
//#line 58 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	CodeBlock codeBlock = (CodeBlock)val_peek(1).obj;
  	codeBlock.getStatements().add((IStatement)val_peek(0).obj);
  	yyval = val_peek(1);
 }
break;
case 5:
//#line 65 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(1);
 }
break;
case 6:
//#line 70 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 7:
//#line 73 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal(new ExpressionStatement((IExpression)val_peek(0).obj));
 }
break;
case 8:
//#line 76 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = val_peek(0);
 }
break;
case 9:
//#line 79 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = val_peek(0);
 }
break;
case 10:
//#line 82 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 11:
//#line 85 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 12:
//#line 88 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = val_peek(0);
 }
break;
case 13:
//#line 93 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	BreakStatement stmt = new BreakStatement();
	yyval = new ParserVal(stmt);
 }
break;
case 14:
//#line 99 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ReturnStatement stmt = new ReturnStatement((IExpression)val_peek(0).obj);
	yyval = new ParserVal(stmt);
 }
break;
case 15:
//#line 103 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ReturnStatement stmt = new ReturnStatement();
	yyval = new ParserVal(stmt);
 }
break;
case 16:
//#line 109 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 17:
//#line 114 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	Variable var = (Variable)val_peek(4).obj;
	IExpression collection = (IExpression)val_peek(2).obj;
	CodeBlock body = (CodeBlock)val_peek(0).obj;

	LoopStatement stmt = new LoopStatement();
	stmt.setLoopVariable(var);
	stmt.setLoopCollection(collection);
	stmt.setLoopBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 18:
//#line 127 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(1);
 }
break;
case 19:
//#line 132 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 20:
//#line 135 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ConditionalStatement conditionalStatement = (ConditionalStatement)val_peek(1).obj;
	conditionalStatement.setNegativeStatements((CodeBlock)val_peek(0).obj);
	yyval = val_peek(1);
 }
break;
case 21:
//#line 142 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ConditionalStatement conditionalStatement = new ConditionalStatement();
	conditionalStatement.setCondition((IExpression)val_peek(2).obj);
	conditionalStatement.setPositiveStatements((CodeBlock)val_peek(0).obj);
	yyval = new ParserVal(conditionalStatement);
 }
break;
case 22:
//#line 150 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal((CodeBlock)val_peek(0).obj);
 }
break;
case 23:
//#line 155 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	FunctionDefinition functionDefinition = new FunctionDefinition();
	functionDefinition.setBody((CodeBlock)val_peek(0).obj);
	functionDefinition.setFunctionName(val_peek(4).sval);
	functionDefinition.setArgumentNames((List<String>)val_peek(2).obj);
	yyval = new ParserVal(functionDefinition);
 }
break;
case 24:
//#line 164 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal(new LinkedList<String>());
 }
break;
case 25:
//#line 167 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	List<String> argumentList = (List<String>)val_peek(2).obj;
        argumentList.add(val_peek(0).sval);
        yyval = val_peek(2);
 }
break;
case 26:
//#line 172 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	List<String> argumentList = new LinkedList<>();
 	argumentList.add(val_peek(0).sval);
 	yyval = new ParserVal(argumentList);
 }
break;
case 27:
//#line 179 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	AssignmentStatement stmt = new VariableAssignmentStatement();
	stmt.setLeftHandOperand((Variable)val_peek(2).obj);
	stmt.setRightHandOperand((IExpression)val_peek(0).obj);
	yyval = new ParserVal(stmt);
 }
break;
case 28:
//#line 185 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	AssignmentStatement stmt = new ArrayElementAssignmentStatement();
	stmt.setLeftHandOperand((IExpression)val_peek(2).obj);
	stmt.setRightHandOperand((IExpression)val_peek(0).obj);
	yyval = new ParserVal(stmt);
 }
break;
case 29:
//#line 193 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal(new Variable(val_peek(0).sval));
 }
break;
case 30:
//#line 198 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 31:
//#line 201 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 32:
//#line 204 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 33:
//#line 207 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 34:
//#line 210 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 35:
//#line 215 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ArrayDereferenceExpression expr = new ArrayDereferenceExpression();
	expr.setArrayExpression((IExpression)val_peek(3).obj);
	expr.setIndexExpression((IExpression)val_peek(1).obj);
	yyval = new ParserVal(expr);
 }
break;
case 36:
//#line 223 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 37:
//#line 226 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = val_peek(0);
 }
break;
case 38:
//#line 231 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	UnaryOperationExpression expr = new UnaryOperationExpression();
	expr.setOperand((IExpression)val_peek(0).obj);
	expr.setOperator((IUnaryOperator)val_peek(1).obj);
	yyval = new ParserVal(expr);
 }
break;
case 39:
//#line 239 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal(new UnaryMinus());
 }
break;
case 40:
//#line 242 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = new ParserVal(new Not());
 }
break;
case 41:
//#line 247 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Plus());
 }
break;
case 42:
//#line 250 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Minus());
 }
break;
case 43:
//#line 253 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Mul());
 }
break;
case 44:
//#line 256 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Div());
 }
break;
case 45:
//#line 259 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Gt());
 }
break;
case 46:
//#line 262 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Lt());
 }
break;
case 47:
//#line 265 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Eq());
 }
break;
case 48:
//#line 268 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new And());
 }
break;
case 49:
//#line 271 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = constructBinaryOperation((IExpression)val_peek(2).obj, (IExpression)val_peek(0).obj, new Or());
 }
break;
case 50:
//#line 274 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = val_peek(1);
 }
break;
case 51:
//#line 279 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = new ParserVal(new IntegerLiteral(val_peek(0).ival));
 }
break;
case 52:
//#line 282 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = new ParserVal(new StringLiteral(val_peek(0).sval));
 }
break;
case 53:
//#line 285 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = new ParserVal(new BooleanLiteral(true));
 }
break;
case 54:
//#line 288 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
  	yyval = new ParserVal(new BooleanLiteral(false));
 }
break;
case 55:
//#line 291 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 56:
//#line 294 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	yyval = new ParserVal(new NullLiteral());
 }
break;
case 57:
//#line 299 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	ArrayLiteral literal = new ArrayLiteral();
	literal.setElements((List<IExpression>)val_peek(1).obj);
	yyval = new ParserVal(literal);
 }
break;
case 58:
//#line 306 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	FunctionCallExpression functionCall = new FunctionCallExpression();
	functionCall.setFunctionName(val_peek(3).sval);
	functionCall.setFunctionCallArguments((List<IExpression>)val_peek(1).obj);
	yyval = new ParserVal(functionCall);
 }
break;
case 59:
//#line 312 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	FunctionCallExpression expr = (FunctionCallExpression)val_peek(0).obj;
	expr.getFunctionCallArguments().add(0,(IExpression)val_peek(2).obj);
	yyval = val_peek(0);
 }
break;
case 60:
//#line 319 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	yyval = val_peek(0);
 }
break;
case 61:
//#line 324 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	List<IExpression> argumentList = new LinkedList<>();
        yyval = new ParserVal(argumentList);
 }
break;
case 62:
//#line 328 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
 	List<IExpression> argumentList = (List<IExpression>)val_peek(2).obj;
 	IExpression expression = (IExpression)val_peek(0).obj;
 	argumentList.add(expression);
 	yyval = val_peek(2);
 }
break;
case 63:
//#line 334 "/home/amalnev/dev/solarium/src/main/yacc/solarium.y"
{
	List<IExpression> argumentList = new LinkedList<>();
	IExpression expression = (IExpression)val_peek(0).obj;
	argumentList.add(expression);
	yyval = new ParserVal(argumentList);
 }
break;
//#line 997 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
