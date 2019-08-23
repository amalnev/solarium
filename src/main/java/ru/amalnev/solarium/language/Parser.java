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



//#line 2 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
import java.util.*;
import java.util.function.*;
import java.io.*;
import lombok.Getter;
import ru.amalnev.solarium.language.operators.*;
import ru.amalnev.solarium.language.expressions.*;
import ru.amalnev.solarium.language.statements.*;
//#line 25 "Parser.java"




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
public final static short SEMICOLON=257;
public final static short IDENTIFIER=258;
public final static short LE_OP=259;
public final static short GE_OP=260;
public final static short EQ_OP=261;
public final static short NE_OP=262;
public final static short AND_OP=263;
public final static short OR_OP=264;
public final static short OPEN_SQUARE_BRACKET=265;
public final static short CLOSE_SQUARE_BRACKET=266;
public final static short OPEN_BRACKET=267;
public final static short CLOSE_BRACKET=268;
public final static short COMMA=269;
public final static short MINUS=270;
public final static short EXCLAMATION_MARK=271;
public final static short PLUS=272;
public final static short MUL=273;
public final static short DIV=274;
public final static short MODULO=275;
public final static short LT=276;
public final static short GT=277;
public final static short EQ=278;
public final static short DOT=279;
public final static short NULL=280;
public final static short IF=281;
public final static short ELSE=282;
public final static short WHILE=283;
public final static short DO=284;
public final static short FOR=285;
public final static short NUMERIC_LITERAL=286;
public final static short STRING_LITERAL=287;
public final static short TRUE=288;
public final static short FALSE=289;
public final static short OPEN_CURLY_BRACKET=290;
public final static short CLOSE_CURLY_BRACKET=291;
public final static short COLON=292;
public final static short CONTINUE=293;
public final static short BREAK=294;
public final static short RETURN=295;
public final static short FUNCTION=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    2,    2,    2,
    8,    8,    9,    9,    7,    7,    7,    7,    6,    6,
    6,    6,    6,    5,    5,    4,    4,    3,   11,   11,
   11,   11,   11,   11,   12,   12,   12,   12,   12,   12,
   12,   13,   13,   15,   15,   16,   16,   17,   17,   17,
   17,   18,   18,   18,   19,   19,   19,   19,   19,   20,
   20,   20,   21,   21,   22,   22,   14,   14,   10,
};
final static short yylen[] = {                            2,
    0,    1,    1,    2,    1,    1,    1,    1,    1,    1,
    6,    5,    1,    3,    2,    2,    2,    3,    5,    7,
    6,    7,    7,    5,    7,    2,    3,    2,    1,    4,
    3,    4,    6,    5,    1,    1,    1,    1,    1,    1,
    3,    1,    3,    1,    1,    1,    2,    1,    3,    3,
    3,    1,    3,    3,    1,    3,    3,    3,    3,    1,
    3,    3,    1,    3,    1,    3,    1,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   44,   45,   40,    0,    0,    0,    0,   36,
   37,   38,   39,    0,    0,    0,    0,    0,    0,    0,
    3,    5,    6,    7,    8,    9,   10,    0,    0,   29,
   69,    0,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   26,    0,   15,   16,   17,
    0,    0,    4,   28,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   31,    0,   42,   41,    0,    0,    0,    0,    0,
   27,   18,    0,    0,   68,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,    0,    0,   13,    0,    0,
   30,    0,   43,    0,   19,    0,    0,    0,    0,   12,
    0,    0,   34,    0,    0,    0,    0,   21,    0,   11,
   14,   33,   25,   20,   23,   22,
};
final static short yydgoto[] = {                         19,
   20,   21,   22,   23,   24,   25,   26,   27,  110,   28,
   29,   30,   73,   31,   32,   33,   34,   35,   36,   37,
   38,   39,
};
final static short yysindex[] = {                      -234,
 -261,  -13,    0,    0,    0, -256, -250, -234, -244,    0,
    0,    0,    0, -156, -222, -216,  -82, -192,    0, -234,
    0,    0,    0,    0,    0,    0,    0, -189, -258,    0,
    0,   40,    0, -259, -238, -251, -233, -166, -160,  -77,
 -152,  -13,  -13, -165,   35,    0, -122,    0,    0,    0,
 -137, -141,    0,    0,  -13,  -13, -117, -252,   40,   40,
   40,  -13,  -13,  -13,  -13,  -13,  -13,  -13,  -13,  -13,
  -13,    0, -197,    0,    0, -121, -116, -110, -262,  -13,
    0,    0, -195, -120,    0, -107, -252, -252, -252, -252,
 -259, -259, -238, -238, -238, -238, -251, -251, -233, -166,
    0,  -13, -234, -234,  -13,  -13,  -50,    0, -136, -176,
    0,  -45,    0, -112,    0,  -91,  -90, -234,  -89,    0,
 -136,  -78,    0, -146, -234,  -75, -234,    0, -234,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                       183,
   73,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  184,
    0,    0,    0,    0,    0,    0,    0,    0,  119,    0,
    0,    0,    0,  224,  287, -163, -113, -199, -226,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   96,    0,
    0,    0,    0,    0,    0,    0,  161,  182,  203,  119,
  245,  266,  308,  329,  350,  371,  388,  398,  -17,   -4,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  172,   -8,  -42,  -99,    0,    0,    0,    0,    0,    2,
   16,    0,   75,  -38,    0,    0,   50,   24,   74,  122,
  124,    0,
};
final static int YYTABLESIZE=667;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   24,   74,   80,   41,   40,   40,   55,   64,   65,  120,
   42,   53,   55,   59,   60,   61,   43,   85,   51,   56,
   57,  130,   45,    1,   66,   67,   57,   68,   69,  106,
   67,   62,    2,   63,   48,    3,    4,  107,   53,   67,
   49,   67,   67,   76,   77,    5,    6,   58,    7,    8,
    9,   10,   11,   12,   13,   14,   84,   65,   15,   16,
   17,   18,  108,  113,   65,   52,   65,   54,   65,   65,
  101,  102,  109,   74,   87,   88,   89,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   93,   94,   95,
   96,  121,  122,   60,  114,  115,   70,   60,   60,   60,
   60,    1,   60,   71,   60,   60,  116,  117,  119,  128,
    2,   91,   92,    3,    4,   75,  133,   78,  135,   82,
  136,  132,  102,    5,    6,   83,    7,    8,    9,   10,
   11,   12,   13,   14,   46,    1,   15,   16,   17,   18,
   86,   97,   98,   63,    2,  111,  103,    3,    4,   63,
   63,  104,   63,   14,   63,   63,  105,    5,    6,  112,
    7,    8,    9,   10,   11,   12,   13,   14,   81,  125,
   15,   16,   17,   18,   50,    1,  126,  127,  129,  131,
    1,  134,    1,    2,    2,   47,  124,    3,    4,    2,
   72,   99,    3,    4,  100,    0,    0,    5,    0,    0,
    0,    0,    5,   10,   11,   12,   13,    1,   10,   11,
   12,   13,    1,    0,    0,    0,    2,  118,    0,    3,
    4,    2,  123,    0,    3,    4,    0,    0,    0,    5,
    0,    0,    0,    0,    5,   10,   11,   12,   13,   64,
   10,   11,   12,   13,    1,   64,   64,    0,   64,    0,
   64,   64,   66,    2,    0,    0,    3,    4,   24,   66,
    0,   66,    0,   66,   66,    0,    5,   24,    0,    0,
   24,   24,   10,   11,   12,   13,    0,    0,    0,    0,
   24,   24,    0,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   79,   24,   24,   24,   24,    1,    0,    0,
    0,    2,    0,    0,    3,    4,    2,    0,    0,    0,
    0,    0,    0,    0,    5,    0,    0,    0,    0,    5,
   10,   11,   12,   13,    0,   10,   11,   12,   13,   35,
    0,   35,   35,   35,   35,   35,   35,   35,   35,    0,
   35,   35,   35,    0,   35,   35,   35,   35,   35,   35,
   35,   35,   35,    0,   35,   35,   35,   35,   35,   35,
   35,    0,    0,    0,    0,   35,    0,   35,   35,   35,
   35,   35,   35,   35,   35,   46,    0,   46,   46,   46,
   46,   46,   46,    0,   46,    0,   46,   46,   46,    0,
   46,   46,   46,   46,   46,   46,   47,    0,   47,   47,
   47,   47,   47,   47,    0,   47,    0,   47,   47,   47,
    0,   47,   47,   47,   47,   47,   47,   49,    0,   49,
   49,   49,   49,   49,   49,    0,   49,    0,   49,   49,
   49,    0,   49,   49,   49,   49,   49,   49,   50,    0,
   50,   50,   50,   50,   50,   50,    0,   50,    0,   50,
   50,   50,    0,   50,   50,   50,   50,   50,   50,   51,
    0,   51,   51,   51,   51,   51,   51,    0,   51,    0,
   51,   51,   51,    0,   51,   51,   51,   51,   51,   51,
   52,    0,   52,   52,   52,   52,   52,   52,    0,   52,
    0,   52,   52,   52,    0,   52,    0,    0,    0,   52,
   52,   54,    0,   54,   54,   54,   54,   54,   54,    0,
   54,    0,   54,   54,   54,    0,   54,    0,    0,    0,
   54,   54,   53,    0,   53,   53,   53,   53,   53,   53,
    0,   53,    0,   53,   53,   53,    0,   53,    0,    0,
    0,   53,   53,   55,    0,   55,   55,   55,   55,   55,
   55,    0,   55,    0,   55,   55,    0,    0,    0,    0,
    0,    0,   55,   55,   58,    0,   58,   58,   58,   58,
   58,   58,    0,   58,    0,   58,   58,    0,    0,    0,
    0,    0,    0,   58,   58,   59,    0,   59,   59,   59,
   59,   59,   59,    0,   59,    0,   59,   59,    0,    0,
    0,    0,    0,    0,   59,   59,   56,    0,   56,   56,
   56,   56,   56,   56,    0,   56,    0,   56,   56,    0,
    0,    0,    0,    0,    0,   56,   56,   57,    0,   57,
   57,   57,   57,   57,   57,    0,   57,    0,   57,   57,
    0,    0,    0,    0,   61,    0,   57,   57,   61,   61,
   61,   61,    0,   61,   62,   61,   61,    0,   62,   62,
   62,   62,    0,   62,    0,   62,   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          8,
    0,   40,   45,    2,  267,  267,  265,  259,  260,  109,
  267,   20,  265,  273,  274,  275,  267,   56,   17,  278,
  279,  121,  267,  258,  276,  277,  279,  261,  262,  292,
  257,  270,  267,  272,  257,  270,  271,   80,   47,  266,
  257,  268,  269,   42,   43,  280,  281,   32,  283,  284,
  285,  286,  287,  288,  289,  290,   55,  257,  293,  294,
  295,  296,  258,  102,  264,  258,  266,  257,  268,  269,
  268,  269,  268,  112,   59,   60,   61,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,   64,   65,   66,
   67,  268,  269,  257,  103,  104,  263,  261,  262,  263,
  264,  258,  266,  264,  268,  269,  105,  106,  107,  118,
  267,   62,   63,  270,  271,  268,  125,  283,  127,  257,
  129,  268,  269,  280,  281,  267,  283,  284,  285,  286,
  287,  288,  289,  290,  291,  258,  293,  294,  295,  296,
  258,   68,   69,  257,  267,  266,  268,  270,  271,  263,
  264,  268,  266,  290,  268,  269,  267,  280,  281,  267,
  283,  284,  285,  286,  287,  288,  289,  290,  291,  282,
  293,  294,  295,  296,  257,  258,  268,  268,  268,  258,
  258,  257,    0,    0,  267,   14,  112,  270,  271,  267,
  268,   70,  270,  271,   71,   -1,   -1,  280,   -1,   -1,
   -1,   -1,  280,  286,  287,  288,  289,  258,  286,  287,
  288,  289,  258,   -1,   -1,   -1,  267,  268,   -1,  270,
  271,  267,  268,   -1,  270,  271,   -1,   -1,   -1,  280,
   -1,   -1,   -1,   -1,  280,  286,  287,  288,  289,  257,
  286,  287,  288,  289,  258,  263,  264,   -1,  266,   -1,
  268,  269,  257,  267,   -1,   -1,  270,  271,  258,  264,
   -1,  266,   -1,  268,  269,   -1,  280,  267,   -1,   -1,
  270,  271,  286,  287,  288,  289,   -1,   -1,   -1,   -1,
  280,  281,   -1,  283,  284,  285,  286,  287,  288,  289,
  290,  291,  258,  293,  294,  295,  296,  258,   -1,   -1,
   -1,  267,   -1,   -1,  270,  271,  267,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,  280,
  286,  287,  288,  289,   -1,  286,  287,  288,  289,  257,
   -1,  259,  260,  261,  262,  263,  264,  265,  266,   -1,
  268,  269,  270,   -1,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   -1,  259,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,  274,
  275,  276,  277,  278,  279,  257,   -1,  259,  260,  261,
  262,  263,  264,   -1,  266,   -1,  268,  269,  270,   -1,
  272,  273,  274,  275,  276,  277,  257,   -1,  259,  260,
  261,  262,  263,  264,   -1,  266,   -1,  268,  269,  270,
   -1,  272,  273,  274,  275,  276,  277,  257,   -1,  259,
  260,  261,  262,  263,  264,   -1,  266,   -1,  268,  269,
  270,   -1,  272,  273,  274,  275,  276,  277,  257,   -1,
  259,  260,  261,  262,  263,  264,   -1,  266,   -1,  268,
  269,  270,   -1,  272,  273,  274,  275,  276,  277,  257,
   -1,  259,  260,  261,  262,  263,  264,   -1,  266,   -1,
  268,  269,  270,   -1,  272,  273,  274,  275,  276,  277,
  257,   -1,  259,  260,  261,  262,  263,  264,   -1,  266,
   -1,  268,  269,  270,   -1,  272,   -1,   -1,   -1,  276,
  277,  257,   -1,  259,  260,  261,  262,  263,  264,   -1,
  266,   -1,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,  277,  257,   -1,  259,  260,  261,  262,  263,  264,
   -1,  266,   -1,  268,  269,  270,   -1,  272,   -1,   -1,
   -1,  276,  277,  257,   -1,  259,  260,  261,  262,  263,
  264,   -1,  266,   -1,  268,  269,   -1,   -1,   -1,   -1,
   -1,   -1,  276,  277,  257,   -1,  259,  260,  261,  262,
  263,  264,   -1,  266,   -1,  268,  269,   -1,   -1,   -1,
   -1,   -1,   -1,  276,  277,  257,   -1,  259,  260,  261,
  262,  263,  264,   -1,  266,   -1,  268,  269,   -1,   -1,
   -1,   -1,   -1,   -1,  276,  277,  257,   -1,  259,  260,
  261,  262,  263,  264,   -1,  266,   -1,  268,  269,   -1,
   -1,   -1,   -1,   -1,   -1,  276,  277,  257,   -1,  259,
  260,  261,  262,  263,  264,   -1,  266,   -1,  268,  269,
   -1,   -1,   -1,   -1,  257,   -1,  276,  277,  261,  262,
  263,  264,   -1,  266,  257,  268,  269,   -1,  261,  262,
  263,  264,   -1,  266,   -1,  268,  269,
};
}
final static short YYFINAL=19;
final static short YYMAXTOKEN=296;
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
null,null,null,"SEMICOLON","IDENTIFIER","LE_OP","GE_OP","EQ_OP","NE_OP",
"AND_OP","OR_OP","OPEN_SQUARE_BRACKET","CLOSE_SQUARE_BRACKET","OPEN_BRACKET",
"CLOSE_BRACKET","COMMA","MINUS","EXCLAMATION_MARK","PLUS","MUL","DIV","MODULO",
"LT","GT","EQ","DOT","NULL","IF","ELSE","WHILE","DO","FOR","NUMERIC_LITERAL",
"STRING_LITERAL","TRUE","FALSE","OPEN_CURLY_BRACKET","CLOSE_CURLY_BRACKET",
"COLON","CONTINUE","BREAK","RETURN","FUNCTION",
};
final static String yyrule[] = {
"$accept : program",
"program :",
"program : statement_list",
"statement_list : statement",
"statement_list : statement_list statement",
"statement : expression_statement",
"statement : compound_statement",
"statement : selection_statement",
"statement : iteration_statement",
"statement : jump_statement",
"statement : function_definition",
"function_definition : FUNCTION IDENTIFIER OPEN_BRACKET identifier_list CLOSE_BRACKET compound_statement",
"function_definition : FUNCTION IDENTIFIER OPEN_BRACKET CLOSE_BRACKET compound_statement",
"identifier_list : IDENTIFIER",
"identifier_list : identifier_list COMMA IDENTIFIER",
"jump_statement : CONTINUE SEMICOLON",
"jump_statement : BREAK SEMICOLON",
"jump_statement : RETURN SEMICOLON",
"jump_statement : RETURN expression SEMICOLON",
"iteration_statement : WHILE OPEN_BRACKET expression CLOSE_BRACKET statement",
"iteration_statement : DO statement WHILE OPEN_BRACKET expression CLOSE_BRACKET SEMICOLON",
"iteration_statement : FOR OPEN_BRACKET expression_statement expression_statement CLOSE_BRACKET statement",
"iteration_statement : FOR OPEN_BRACKET expression_statement expression_statement expression CLOSE_BRACKET statement",
"iteration_statement : FOR OPEN_BRACKET IDENTIFIER COLON expression CLOSE_BRACKET statement",
"selection_statement : IF OPEN_BRACKET expression CLOSE_BRACKET statement",
"selection_statement : IF OPEN_BRACKET expression CLOSE_BRACKET statement ELSE statement",
"compound_statement : OPEN_CURLY_BRACKET CLOSE_CURLY_BRACKET",
"compound_statement : OPEN_CURLY_BRACKET statement_list CLOSE_CURLY_BRACKET",
"expression_statement : expression SEMICOLON",
"postfix_expression : primary_expression",
"postfix_expression : postfix_expression OPEN_SQUARE_BRACKET expression CLOSE_SQUARE_BRACKET",
"postfix_expression : IDENTIFIER OPEN_BRACKET CLOSE_BRACKET",
"postfix_expression : IDENTIFIER OPEN_BRACKET argument_expression_list CLOSE_BRACKET",
"postfix_expression : postfix_expression DOT IDENTIFIER OPEN_BRACKET argument_expression_list CLOSE_BRACKET",
"postfix_expression : postfix_expression DOT IDENTIFIER OPEN_BRACKET CLOSE_BRACKET",
"primary_expression : IDENTIFIER",
"primary_expression : NUMERIC_LITERAL",
"primary_expression : STRING_LITERAL",
"primary_expression : TRUE",
"primary_expression : FALSE",
"primary_expression : NULL",
"primary_expression : OPEN_BRACKET expression CLOSE_BRACKET",
"argument_expression_list : assignment_expression",
"argument_expression_list : argument_expression_list COMMA assignment_expression",
"unary_operator : MINUS",
"unary_operator : EXCLAMATION_MARK",
"unary_expression : postfix_expression",
"unary_expression : unary_operator postfix_expression",
"multiplicative_expression : unary_expression",
"multiplicative_expression : multiplicative_expression MUL postfix_expression",
"multiplicative_expression : multiplicative_expression DIV postfix_expression",
"multiplicative_expression : multiplicative_expression MODULO postfix_expression",
"additive_expression : multiplicative_expression",
"additive_expression : additive_expression PLUS multiplicative_expression",
"additive_expression : additive_expression MINUS multiplicative_expression",
"relational_expression : additive_expression",
"relational_expression : relational_expression LT additive_expression",
"relational_expression : relational_expression GT additive_expression",
"relational_expression : relational_expression LE_OP additive_expression",
"relational_expression : relational_expression GE_OP additive_expression",
"equality_expression : relational_expression",
"equality_expression : equality_expression EQ_OP relational_expression",
"equality_expression : equality_expression NE_OP relational_expression",
"logical_and_expression : equality_expression",
"logical_and_expression : logical_and_expression AND_OP equality_expression",
"logical_or_expression : logical_and_expression",
"logical_or_expression : logical_or_expression OR_OP logical_and_expression",
"assignment_expression : logical_or_expression",
"assignment_expression : postfix_expression EQ assignment_expression",
"expression : assignment_expression",
};

//#line 407 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"

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
//#line 490 "Parser.java"
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
//#line 24 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{ /*An empty program*/
	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody(null);
	this.entryPointFunction = entryPoint;
 }
break;
case 2:
//#line 29 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody((CompoundStatement)val_peek(0).obj);
	this.entryPointFunction = entryPoint;
 }
break;
case 3:
//#line 37 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = new CompoundStatement();
	stmt.getStatements().add((IStatement)val_peek(0).obj);
	yyval = new ParserVal(stmt);
 }
break;
case 4:
//#line 42 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = (CompoundStatement)val_peek(1).obj;
	stmt.getStatements().add((IStatement)val_peek(0).obj);
	yyval = val_peek(1);
 }
break;
case 5:
//#line 50 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 6:
//#line 53 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 7:
//#line 56 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 8:
//#line 59 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 9:
//#line 62 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 10:
//#line 65 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 11:
//#line 71 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	String functionName = val_peek(4).sval;
	List<String> argumentNames = (List<String>) val_peek(2).obj;
	CompoundStatement body = (CompoundStatement) val_peek(0).obj;
	FunctionDefinition stmt = new FunctionDefinition();
	stmt.setFunctionName(functionName);
	stmt.setArgumentNames(argumentNames);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 12:
//#line 81 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	String functionName = val_peek(3).sval;
	CompoundStatement body = (CompoundStatement) val_peek(0).obj;
	FunctionDefinition stmt = new FunctionDefinition();
	stmt.setFunctionName(functionName);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 13:
//#line 92 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<String> identifierList = new ArrayList<>();
 	identifierList.add(val_peek(0).sval);
 	yyval = new ParserVal(identifierList);
 }
break;
case 14:
//#line 97 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<String> identifierList = (List<String>) val_peek(2).obj;
 	identifierList.add(val_peek(0).sval);
 	yyval = val_peek(2);
 }
break;
case 15:
//#line 105 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ContinueStatement());
 }
break;
case 16:
//#line 108 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new BreakStatement());
 }
break;
case 17:
//#line 111 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ReturnStatement());
 }
break;
case 18:
//#line 114 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ReturnStatement((IExpression) val_peek(1).obj));
 }
break;
case 19:
//#line 120 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IExpression condition = (IExpression) val_peek(2).obj;
	IStatement body = (IStatement) val_peek(0).obj;
	IterationStatement stmt = new WhileIterationStatement();
	stmt.setCondition(condition);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 20:
//#line 128 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression condition = (IExpression) val_peek(2).obj;
        IStatement body = (IStatement) val_peek(5).obj;
        IterationStatement stmt = new DoWhileIterationStatement();
	stmt.setCondition(condition);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 21:
//#line 136 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IStatement initializer = (IStatement) val_peek(3).obj;
 	ExpressionStatement conditionStatement = (ExpressionStatement) val_peek(2).obj;
 	IStatement body = (IStatement) val_peek(0).obj;
 	ForIterationStatement stmt = new ForIterationStatement();
 	stmt.setCondition(conditionStatement.getExpression());
 	stmt.setInitializer(initializer);
 	stmt.setBody(body);
 	yyval = new ParserVal(stmt);
 }
break;
case 22:
//#line 146 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IStatement initializer = (IStatement) val_peek(4).obj;
	ExpressionStatement conditionStatement = (ExpressionStatement) val_peek(3).obj;
	IExpression postIteration = (IExpression) val_peek(2).obj;
	IStatement body = (IStatement) val_peek(0).obj;
	ForIterationStatement stmt = new ForIterationStatement();
	stmt.setCondition(conditionStatement.getExpression());
	stmt.setInitializer(initializer);
	stmt.setPostIterationExpression(postIteration);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 23:
//#line 158 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	String elementName = val_peek(4).sval;
	IExpression collectionExpression = (IExpression) val_peek(2).obj;
	IStatement body = (IStatement) val_peek(0).obj;
	CollectionIterationStatement stmt = new CollectionIterationStatement();
	stmt.setElementName(elementName);
	stmt.setCollectionExpression(collectionExpression);
	stmt.setBody(body);
	yyval = new ParserVal(stmt);
 }
break;
case 24:
//#line 171 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression condition = (IExpression) val_peek(2).obj;
 	IStatement positiveStatement = (IStatement) val_peek(0).obj;
 	SelectionStatement stmt = new SelectionStatement(condition, positiveStatement);
 	yyval = new ParserVal(stmt);
 }
break;
case 25:
//#line 177 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression condition = (IExpression) val_peek(4).obj;
        IStatement positiveStatement = (IStatement) val_peek(2).obj;
        IStatement negativeStatement = (IStatement) val_peek(0).obj;
        SelectionStatement stmt = new SelectionStatement(condition, positiveStatement, negativeStatement);
        yyval = new ParserVal(stmt);
 }
break;
case 26:
//#line 187 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = new CompoundStatement();
 	yyval = new ParserVal(stmt);
 }
break;
case 27:
//#line 191 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(1);
 }
break;
case 28:
//#line 197 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression expr = (IExpression) val_peek(1).obj;
 	ExpressionStatement stmt = new ExpressionStatement(expr);
 	yyval = new ParserVal(stmt);
 }
break;
case 29:
//#line 205 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 30:
//#line 208 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IExpression arrayExpression = (IExpression) val_peek(3).obj;
	IExpression indexExpression = (IExpression) val_peek(1).obj;
	ArrayDereferenceExpression expr = new ArrayDereferenceExpression();
	expr.setArrayExpression(arrayExpression);
	expr.setIndexExpression(indexExpression);
	yyval = new ParserVal(expr);
 }
break;
case 31:
//#line 216 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName(val_peek(2).sval);
	yyval = new ParserVal(expr);
 }
break;
case 32:
//#line 221 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = (List<IExpression>) val_peek(1).obj;
 	FunctionCallExpression expr = new FunctionCallExpression();
 	expr.setFunctionName(val_peek(3).sval);
 	expr.setFunctionCallArguments(arguments);
 	yyval = new ParserVal(expr);
 }
break;
case 33:
//#line 228 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	List<IExpression> arguments = (List<IExpression>) val_peek(1).obj;
	IExpression implicitArgument = (IExpression) val_peek(5).obj;
	arguments.add(0, implicitArgument);
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName(val_peek(3).sval);
	expr.setFunctionCallArguments(arguments);
	yyval = new ParserVal(expr);
 }
break;
case 34:
//#line 237 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = new ArrayList<>();
	IExpression implicitArgument = (IExpression) val_peek(4).obj;
	arguments.add(0, implicitArgument);
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName(val_peek(2).sval);
	expr.setFunctionCallArguments(arguments);
	yyval = new ParserVal(expr);
 }
break;
case 35:
//#line 249 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	VariableExpression expr = new VariableExpression(val_peek(0).sval);
	yyval = new ParserVal(expr);
 }
break;
case 36:
//#line 253 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IntegerLiteralExpression expr = new IntegerLiteralExpression((Integer) val_peek(0).obj);
	yyval = new ParserVal(expr);
 }
break;
case 37:
//#line 257 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	StringLiteralExpression expr = new StringLiteralExpression(val_peek(0).sval);
	yyval = new ParserVal(expr);
 }
break;
case 38:
//#line 261 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	BooleanLiteralExpression expr = new BooleanLiteralExpression(true);
	yyval = new ParserVal(expr);
 }
break;
case 39:
//#line 265 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	BooleanLiteralExpression expr = new BooleanLiteralExpression(false);
        yyval = new ParserVal(expr);
 }
break;
case 40:
//#line 269 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	NullLiteralExpression expr = new NullLiteralExpression();
 	yyval = new ParserVal(expr);
 }
break;
case 41:
//#line 273 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(1);
 }
break;
case 42:
//#line 279 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = new ArrayList<>();
 	arguments.add((IExpression)val_peek(0).obj);
 	yyval = new ParserVal(arguments);
 }
break;
case 43:
//#line 284 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	List<IExpression> arguments = (List<IExpression>) val_peek(2).obj;
	arguments.add((IExpression) val_peek(0).obj);
	yyval = val_peek(2);
 }
break;
case 44:
//#line 292 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = new UnaryMinus();
 	yyval = new ParserVal(op);
 }
break;
case 45:
//#line 296 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = new Not();
 	yyval = new ParserVal(op);
 }
break;
case 46:
//#line 303 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	yyval = val_peek(0);
 }
break;
case 47:
//#line 306 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = (IUnaryOperator) val_peek(1).obj;
 	UnaryOperationExpression expr = new UnaryOperationExpression(op, (IExpression)val_peek(0).obj);
 	yyval = new ParserVal(expr);
 }
break;
case 48:
//#line 314 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 49:
//#line 317 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	yyval = constructBinaryOperation(val_peek(2), new Mul(), val_peek(0));
 }
break;
case 50:
//#line 320 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Div(), val_peek(0));
 }
break;
case 51:
//#line 323 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Modulo(), val_peek(0));
 }
break;
case 52:
//#line 329 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 53:
//#line 332 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Plus(), val_peek(0));
 }
break;
case 54:
//#line 335 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Minus(), val_peek(0));
 }
break;
case 55:
//#line 341 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 56:
//#line 344 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Lt(), val_peek(0));
 }
break;
case 57:
//#line 347 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Gt(), val_peek(0));
 }
break;
case 58:
//#line 350 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Le(), val_peek(0));
 }
break;
case 59:
//#line 353 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Ge(), val_peek(0));
 }
break;
case 60:
//#line 359 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 61:
//#line 362 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Eq(), val_peek(0));
 }
break;
case 62:
//#line 365 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Neq(), val_peek(0));
 }
break;
case 63:
//#line 371 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 64:
//#line 374 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new And(), val_peek(0));
 }
break;
case 65:
//#line 380 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 66:
//#line 383 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = constructBinaryOperation(val_peek(2), new Or(), val_peek(0));
 }
break;
case 67:
//#line 389 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 68:
//#line 392 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IExpression leftOperand = (IExpression) val_peek(2).obj;
	IExpression rightOperand = (IExpression) val_peek(0).obj;
	AssignmentExpression expr = new AssignmentExpression(leftOperand, rightOperand);
	yyval = new ParserVal(expr);
 }
break;
case 69:
//#line 401 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
//#line 1160 "Parser.java"
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
