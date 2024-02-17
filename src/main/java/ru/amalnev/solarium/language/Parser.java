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

import lombok.Getter;
import ru.amalnev.solarium.language.expressions.*;
import ru.amalnev.solarium.language.operators.*;
import ru.amalnev.solarium.language.statements.*;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
//#line 26 "Parser.java"




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
public final static short GLOBAL=297;
    public final static short ARROW = 298;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    2,    2,    2,
    8,    8,    9,    9,    7,    7,    7,    7,    6,    6,
    6,    6,    6,    5,    5,    4,    4,    3,   11,   11,
        11, 11, 11, 11, 11, 11, 12, 12, 12, 12,
        12, 12, 12, 12, 14, 14, 13, 13, 16, 16,
        17, 17, 18, 18, 18, 18, 19, 19, 19, 20,
        20, 20, 20, 20, 21, 21, 21, 22, 22, 23,
        23, 15, 15, 15, 10,
};
final static short yylen[] = {                            2,
    0,    1,    1,    2,    1,    1,    1,    1,    1,    1,
    6,    5,    1,    3,    2,    2,    2,    3,    5,    7,
    6,    7,    7,    5,    7,    2,    3,    2,    1,    4,
        3, 4, 3, 6, 5, 5, 1, 1, 1, 1,
        1, 1, 3, 1, 3, 2, 1, 3, 1, 1,
        1, 2, 1, 3, 3, 3, 1, 3, 3, 1,
        3, 3, 3, 3, 1, 3, 3, 1, 3, 1,
        3, 1, 3, 4, 1,
};
final static short yydefred[] = {                         0,
        0, 0, 0, 49, 50, 42, 0, 0, 0, 0,
        38, 39, 40, 41, 0, 0, 0, 0, 0, 0,
    0,    0,    3,    5,    6,    7,    8,    9,   10,    0,
        0, 29, 44, 75, 0, 53, 0, 0, 0, 0,
        0, 0, 0, 46, 0, 47, 0, 0, 0, 0,
        0, 0, 0, 26, 0, 15, 16, 17, 0, 0,
        0, 4, 28, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        31, 0, 45, 0, 0, 0, 43, 0, 0, 0,
        0, 0, 27, 18, 0, 0, 0, 73, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 32, 48, 0, 14, 0, 0, 0,
        0, 0, 13, 0, 0, 74, 30, 0, 36, 0,
        19, 0, 0, 0, 0, 12, 0, 35, 0, 0,
        0, 0, 21, 0, 11, 34, 25, 20, 23, 22,
};
final static short yydgoto[] = {                         21,
        22, 23, 24, 25, 26, 27, 28, 29, 48, 30,
   31,   32,   45,   33,   34,   35,   36,   37,   38,   39,
   40,   41,   42,
};
    final static short yysindex[] = {-75,
            -260, -42, 96, 0, 0, 0, -252, -249, -75, -235,
            0, 0, 0, 0, -143, -221, -210, -246, -196, -190,
            0, -75, 0, 0, 0, 0, 0, 0, 0, -173,
            -256, 0, 0, 0, -232, 0, -209, -233, -207, -102,
            -176, -171, 35, 0, -239, 0, -260, -16, -165, 129,
            129, -175, 143, 0, -109, 0, 0, 0, -144, -149,
            -155, 0, 0, 129, 129, -132, -251, -232, -232, -232,
            176, 176, 176, 176, 176, 176, 176, 176, 176, 176,
            0, -12, 0, 129, -169, -126, 0, -134, -129, -131,
            -261, 129, 0, 0, -248, 129, -111, 0, -97, -251,
            -251, -251, -251, -209, -209, -233, -233, -233, -233, -207,
            -207, -102, -176, 0, 0, -125, 0, -75, -75, 129,
            129, 49, 0, -125, 9, 0, 0, 82, 0, -115,
            0, -95, -79, -75, -71, 0, -125, 0, 11, -75,
            -58, -75, 0, -75, 0, 0, 0, 0, 0, 0,
    };
    final static short yyrindex[] = {201,
            209, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 204, 0, 0, 0, 0, 0, 0, 0, 0,
            299, 0, 0, 0, 0, 0, 404, -27, -172, -152,
            -66, 7, 0, 0, 0, 0, 276, 0, 0, 0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0, 0, 0, 0, 0, 0, 320, 0, 0, 0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            255, 0, 0, 0, 0, 0, 0, 0, 232, 341,
            362, 383, 299, 425, 446, 467, 488, 509, 530, -162,
            191, -100, -6, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
        192, -9, -48, -108, 0, 0, 0, 0, 136, -1,
        3, 0, -40, 0, 2, 0, 0, 122, -15, 149,
        138, 160, 0,
};
    final static int YYTABLESIZE = 807;
static short yytable[];
static { yytable();}
static void yytable(){
    yytable = new short[]{52,
            24, 49, 82, 46, 92, 43, 43, 129, 64, 123,
            58, 1, 62, 64, 50, 136, 59, 51, 2, 124,
            3, 65, 66, 4, 5, 1, 83, 66, 145, 84,
            121, 53, 2, 6, 3, 56, 71, 67, 72, 11,
            12, 13, 14, 122, 46, 62, 57, 6, 88, 89,
            20, 73, 74, 11, 12, 13, 14, 106, 107, 108,
            109, 60, 97, 68, 69, 70, 98, 61, 75, 76,
            100, 101, 102, 103, 103, 103, 103, 103, 103, 103,
            103, 103, 103, 63, 65, 115, 79, 139, 65, 65,
            65, 65, 80, 65, 66, 65, 65, 126, 66, 66,
            66, 66, 87, 66, 68, 66, 66, 90, 130, 131,
            68, 68, 94, 68, 1, 68, 68, 95, 132, 133,
            135, 2, 96, 3, 143, 99, 4, 5, 116, 46,
            147, 117, 149, 118, 150, 120, 6, 7, 119, 8,
            9, 10, 11, 12, 13, 14, 15, 54, 1, 16,
            17, 18, 19, 20, 127, 2, 69, 3, 77, 78,
            4, 5, 69, 69, 15, 69, 140, 69, 69, 128,
            6, 7, 141, 8, 9, 10, 11, 12, 13, 14,
            15, 93, 1, 16, 17, 18, 19, 20, 142, 2,
            70, 3, 104, 105, 4, 5, 144, 70, 148, 70,
            1, 70, 70, 2, 6, 7, 55, 8, 9, 10,
            11, 12, 13, 14, 15, 1, 112, 16, 17, 18,
            19, 20, 2, 44, 3, 110, 111, 4, 5, 60,
            125, 60, 60, 60, 60, 60, 60, 6, 60, 113,
            60, 60, 0, 11, 12, 13, 14, 0, 60, 60,
            71, 85, 86, 0, 20, 114, 84, 71, 24, 71,
            0, 71, 71, 72, 0, 24, 0, 24, 0, 0,
            24, 24, 72, 0, 72, 72, 137, 86, 146, 84,
   24,   24,    0,   24,   24,   24,   24,   24,   24,   24,
   24,   24,    1,   24,   24,   24,   24,   24,    0,    2,
            0, 3, 81, 0, 4, 5, 1, 0, 0, 0,
            0, 0, 0, 2, 6, 3, 134, 0, 4, 5,
   11,   12,   13,   14,    0,    0,    0,    0,    6,    0,
    0,   20,    0,    0,   11,   12,   13,   14,    0,    1,
            0, 0, 0, 0, 0, 20, 2, 0, 3, 138,
            0, 4, 5, 47, 0, 0, 0, 0, 0, 0,
    2,    6,    3,    0,    0,    4,    5,   11,   12,   13,
   14,    0,    0,    0,    0,    6,    0,    0,   20,    0,
    0,   11,   12,   13,   14,    0,    1,    0,    0,    0,
    0,    0,   20,    2,    0,    3,    0,    0,    4,    5,
            91, 0, 0, 0, 0, 0, 0, 2, 6, 3,
            0, 0, 4, 5, 11, 12, 13, 14, 0, 0,
            0, 0, 6, 0, 0, 20, 0, 0, 11, 12,
            13, 14, 0, 1, 0, 0, 0, 0, 0, 20,
            2, 0, 3, 0, 0, 4, 5, 67, 0, 0,
            0, 67, 67, 67, 67, 6, 67, 0, 67, 67,
            0, 11, 12, 13, 14, 37, 0, 37, 37, 37,
            37, 37, 37, 37, 37, 0, 37, 37, 37, 0,
            37, 37, 37, 37, 37, 37, 37, 37, 33, 0,
            33, 33, 33, 33, 33, 33, 33, 33, 0, 33,
            33, 33, 0, 33, 33, 33, 33, 33, 33, 33,
            33, 37, 0, 37, 37, 37, 37, 37, 37, 37,
            0, 0, 0, 0, 37, 0, 37, 37, 37, 37,
            37, 37, 37, 37, 37, 37, 37, 37, 37, 37,
            37, 0, 0, 13, 13, 37, 0, 37, 37, 37,
            37, 37, 37, 37, 37, 51, 0, 51, 51, 51,
            51, 51, 51, 0, 51, 0, 51, 51, 51, 0,
            51, 51, 51, 51, 51, 51, 52, 0, 52, 52,
            52, 52, 52, 52, 0, 52, 0, 52, 52, 52,
            0, 52, 52, 52, 52, 52, 52, 54, 0, 54,
            54, 54, 54, 54, 54, 0, 54, 0, 54, 54,
            54, 0, 54, 54, 54, 54, 54, 54, 55, 0,
            55, 55, 55, 55, 55, 55, 0, 55, 0, 55,
            55, 55, 0, 55, 55, 55, 55, 55, 55, 56,
            0, 56, 56, 56, 56, 56, 56, 0, 56, 0,
            56, 56, 56, 0, 56, 56, 56, 56, 56, 56,
            57, 0, 57, 57, 57, 57, 57, 57, 0, 57,
            0, 57, 57, 57, 0, 57, 0, 0, 0, 57,
            57, 59, 0, 59, 59, 59, 59, 59, 59, 0,
            59, 0, 59, 59, 59, 0, 59, 0, 0, 0,
            59, 59, 58, 0, 58, 58, 58, 58, 58, 58,
    0,   58,    0,   58,   58,   58,    0,   58,    0,    0,
            0, 58, 58, 63, 0, 63, 63, 63, 63, 63,
            63, 0, 63, 0, 63, 63, 0, 0, 0, 0,
            0, 0, 63, 63, 64, 0, 64, 64, 64, 64,
            64, 64, 0, 64, 0, 64, 64, 0, 0, 0,
            0, 0, 0, 64, 64, 61, 0, 61, 61, 61,
            61, 61, 61, 0, 61, 0, 61, 61, 0, 0,
            0, 0, 0, 0, 61, 61, 62, 0, 62, 62,
            62, 62, 62, 62, 0, 62, 0, 62, 62, 0,
            0, 0, 0, 0, 0, 62, 62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          9,
        0, 3, 43, 2, 53, 267, 267, 116, 265, 258,
        257, 258, 22, 265, 267, 124, 18, 267, 265, 268,
        267, 278, 279, 270, 271, 258, 266, 279, 137, 269,
        292, 267, 265, 280, 267, 257, 270, 35, 272, 286,
        287, 288, 289, 92, 43, 55, 257, 280, 50, 51,
        297, 259, 260, 286, 287, 288, 289, 73, 74, 75,
        76, 258, 64, 273, 274, 275, 65, 258, 276, 277,
   68,   69,   70,   71,   72,   73,   74,   75,   76,   77,
        78, 79, 80, 257, 257, 84, 263, 128, 261, 262,
        263, 264, 264, 266, 257, 268, 269, 96, 261, 262,
        263, 264, 268, 266, 257, 268, 269, 283, 118, 119,
        263, 264, 257, 266, 258, 268, 269, 267, 120, 121,
        122, 265, 278, 267, 134, 258, 270, 271, 298, 128,
        140, 258, 142, 268, 144, 267, 280, 281, 268, 283,
        284, 285, 286, 287, 288, 289, 290, 291, 258, 293,
        294, 295, 296, 297, 266, 265, 257, 267, 261, 262,
        270, 271, 263, 264, 290, 266, 282, 268, 269, 267,
        280, 281, 268, 283, 284, 285, 286, 287, 288, 289,
        290, 291, 258, 293, 294, 295, 296, 297, 268, 265,
        257, 267, 71, 72, 270, 271, 268, 264, 257, 266,
        0, 268, 269, 0, 280, 281, 15, 283, 284, 285,
        286, 287, 288, 289, 290, 258, 79, 293, 294, 295,
        296, 297, 265, 266, 267, 77, 78, 270, 271, 257,
        95, 259, 260, 261, 262, 263, 264, 280, 266, 80,
        268, 269, -1, 286, 287, 288, 289, -1, 276, 277,
        257, 268, 269, -1, 297, 268, 269, 264, 258, 266,
        -1, 268, 269, 257, -1, 265, -1, 267, -1, -1,
        270, 271, 266, -1, 268, 269, 268, 269, 268, 269,
  280,  281,   -1,  283,  284,  285,  286,  287,  288,  289,
  290,  291,  258,  293,  294,  295,  296,  297,   -1,  265,
   -1,  267,  268,   -1,  270,  271,  258,   -1,   -1,   -1,
   -1,   -1,   -1,  265,  280,  267,  268,   -1,  270,  271,
  286,  287,  288,  289,   -1,   -1,   -1,   -1,  280,   -1,
   -1,  297,   -1,   -1,  286,  287,  288,  289,   -1,  258,
        -1, -1, -1, -1, -1, 297, 265, -1, 267, 268,
   -1,  270,  271,  258,   -1,   -1,   -1,   -1,   -1,   -1,
  265,  280,  267,   -1,   -1,  270,  271,  286,  287,  288,
  289,   -1,   -1,   -1,   -1,  280,   -1,   -1,  297,   -1,
   -1,  286,  287,  288,  289,   -1,  258,   -1,   -1,   -1,
   -1,   -1,  297,  265,   -1,  267,   -1,   -1,  270,  271,
        258, -1, -1, -1, -1, -1, -1, 265, 280, 267,
        -1, -1, 270, 271, 286, 287, 288, 289, -1, -1,
        -1, -1, 280, -1, -1, 297, -1, -1, 286, 287,
        288, 289, -1, 258, -1, -1, -1, -1, -1, 297,
        265, -1, 267, -1, -1, 270, 271, 257, -1, -1,
        -1, 261, 262, 263, 264, 280, 266, -1, 268, 269,
        -1, 286, 287, 288, 289, 257, -1, 259, 260, 261,
        262, 263, 264, 265, 266, -1, 268, 269, 270, -1,
        272, 273, 274, 275, 276, 277, 278, 279, 257, -1,
  259,  260,  261,  262,  263,  264,  265,  266,   -1,  268,
  269,  270,   -1,  272,  273,  274,  275,  276,  277,  278,
  279,  257,   -1,  259,  260,  261,  262,  263,  264,  265,
        -1, -1, -1, -1, 270, -1, 272, 273, 274, 275,
        276, 277, 278, 279, 259, 260, 261, 262, 263, 264,
        265, -1, -1, 268, 269, 270, -1, 272, 273, 274,
        275, 276, 277, 278, 279, 257, -1, 259, 260, 261,
        262, 263, 264, -1, 266, -1, 268, 269, 270, -1,
        272, 273, 274, 275, 276, 277, 257, -1, 259, 260,
        261, 262, 263, 264, -1, 266, -1, 268, 269, 270,
        -1, 272, 273, 274, 275, 276, 277, 257, -1, 259,
  260,  261,  262,  263,  264,   -1,  266,   -1,  268,  269,
  270,   -1,  272,  273,  274,  275,  276,  277,  257,   -1,
  259,  260,  261,  262,  263,  264,   -1,  266,   -1,  268,
  269,  270,   -1,  272,  273,  274,  275,  276,  277,  257,
   -1,  259,  260,  261,  262,  263,  264,   -1,  266,   -1,
  268,  269,  270,   -1,  272,  273,  274,  275,  276,  277,
  257,   -1,  259,  260,  261,  262,  263,  264,   -1,  266,
        -1, 268, 269, 270, -1, 272, -1, -1, -1, 276,
  277,  257,   -1,  259,  260,  261,  262,  263,  264,   -1,
        266, -1, 268, 269, 270, -1, 272, -1, -1, -1,
  276,  277,  257,   -1,  259,  260,  261,  262,  263,  264,
   -1,  266,   -1,  268,  269,  270,   -1,  272,   -1,   -1,
   -1,  276,  277,  257,   -1,  259,  260,  261,  262,  263,
        264, -1, 266, -1, 268, 269, -1, -1, -1, -1,
   -1,   -1,  276,  277,  257,   -1,  259,  260,  261,  262,
  263,  264,   -1,  266,   -1,  268,  269,   -1,   -1,   -1,
   -1,   -1,   -1,  276,  277,  257,   -1,  259,  260,  261,
  262,  263,  264,   -1,  266,   -1,  268,  269,   -1,   -1,
   -1,   -1,   -1,   -1,  276,  277,  257,   -1,  259,  260,
  261,  262,  263,  264,   -1,  266,   -1,  268,  269,   -1,
        -1, -1, -1, -1, -1, 276, 277,
};
}
final static short YYFINAL=21;
    final static short YYMAXTOKEN = 298;
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
        "COLON", "CONTINUE", "BREAK", "RETURN", "FUNCTION", "GLOBAL", "ARROW",
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
"postfix_expression : postfix_expression DOT IDENTIFIER",
"postfix_expression : postfix_expression DOT IDENTIFIER OPEN_BRACKET argument_expression_list CLOSE_BRACKET",
"postfix_expression : postfix_expression DOT IDENTIFIER OPEN_BRACKET CLOSE_BRACKET",
        "postfix_expression : OPEN_BRACKET identifier_list CLOSE_BRACKET ARROW compound_statement",
"primary_expression : IDENTIFIER",
"primary_expression : NUMERIC_LITERAL",
"primary_expression : STRING_LITERAL",
"primary_expression : TRUE",
"primary_expression : FALSE",
"primary_expression : NULL",
"primary_expression : OPEN_BRACKET expression CLOSE_BRACKET",
"primary_expression : array_literal",
"array_literal : OPEN_SQUARE_BRACKET argument_expression_list CLOSE_SQUARE_BRACKET",
"array_literal : OPEN_SQUARE_BRACKET CLOSE_SQUARE_BRACKET",
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
"assignment_expression : GLOBAL IDENTIFIER EQ assignment_expression",
"expression : assignment_expression",
};

//#line 439 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"

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

    //#line 532 "Parser.java"
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
//#line 25 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{ /*An empty program*/
	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody(null);
	this.entryPointFunction = entryPoint;
 }
break;
case 2:
//#line 30 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	FunctionDefinition entryPoint = new FunctionDefinition();
	entryPoint.setBody((CompoundStatement)val_peek(0).obj);
	this.entryPointFunction = entryPoint;
 }
break;
case 3:
//#line 38 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = new CompoundStatement();
	stmt.getStatements().add((IStatement)val_peek(0).obj);
	yyval = new ParserVal(stmt);
 }
break;
case 4:
//#line 43 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = (CompoundStatement)val_peek(1).obj;
	stmt.getStatements().add((IStatement)val_peek(0).obj);
	yyval = val_peek(1);
 }
break;
case 5:
//#line 51 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 6:
//#line 54 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 7:
//#line 57 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 8:
//#line 60 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 9:
//#line 63 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 10:
//#line 66 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 11:
//#line 72 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 82 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 93 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<String> identifierList = new CommaSeparatedList<>();
 	identifierList.add(val_peek(0).sval);
 	yyval = new ParserVal(identifierList);
 }
break;
case 14:
//#line 98 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<String> identifierList = (List<String>) val_peek(2).obj;
 	identifierList.add(val_peek(0).sval);
 	yyval = val_peek(2);
 }
break;
case 15:
//#line 106 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ContinueStatement());
 }
break;
case 16:
//#line 109 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new BreakStatement());
 }
break;
case 17:
//#line 112 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ReturnStatement());
 }
break;
case 18:
//#line 115 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = new ParserVal(new ReturnStatement((IExpression) val_peek(1).obj));
 }
break;
case 19:
//#line 121 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 129 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 137 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 147 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 159 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 172 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression condition = (IExpression) val_peek(2).obj;
 	IStatement positiveStatement = (IStatement) val_peek(0).obj;
 	SelectionStatement stmt = new SelectionStatement(condition, positiveStatement);
 	yyval = new ParserVal(stmt);
 }
break;
case 25:
//#line 178 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression condition = (IExpression) val_peek(4).obj;
        IStatement positiveStatement = (IStatement) val_peek(2).obj;
        IStatement negativeStatement = (IStatement) val_peek(0).obj;
        SelectionStatement stmt = new SelectionStatement(condition, positiveStatement, negativeStatement);
        yyval = new ParserVal(stmt);
 }
break;
case 26:
//#line 188 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	CompoundStatement stmt = new CompoundStatement();
 	yyval = new ParserVal(stmt);
 }
break;
case 27:
//#line 192 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(1);
 }
break;
case 28:
//#line 198 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IExpression expr = (IExpression) val_peek(1).obj;
 	ExpressionStatement stmt = new ExpressionStatement(expr);
 	yyval = new ParserVal(stmt);
 }
break;
case 29:
//#line 206 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
case 30:
//#line 209 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
//#line 217 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName(val_peek(2).sval);
	yyval = new ParserVal(expr);
 }
break;
case 32:
//#line 222 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = (List<IExpression>) val_peek(1).obj;
 	FunctionCallExpression expr = new FunctionCallExpression();
 	expr.setFunctionName(val_peek(3).sval);
 	expr.setFunctionCallArguments(arguments);
 	yyval = new ParserVal(expr);
 }
break;
case 33:
//#line 229 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IExpression sourceExpression = (IExpression) val_peek(2).obj;
	String fieldName = val_peek(0).sval;
	IExpression expr = new FieldAccessExpression(sourceExpression, fieldName);
	yyval = new ParserVal(expr);
 }
break;
case 34:
//#line 235 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
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
case 35:
//#line 244 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = new CommaSeparatedList<>();
	IExpression implicitArgument = (IExpression) val_peek(4).obj;
	arguments.add(0, implicitArgument);
	FunctionCallExpression expr = new FunctionCallExpression();
	expr.setFunctionName(val_peek(2).sval);
	expr.setFunctionCallArguments(arguments);
	yyval = new ParserVal(expr);
 }
break;
case 36:
//#line 253 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{

 }
break;
case 37:
//#line 259 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    VariableExpression expr = new VariableExpression(val_peek(0).sval);
	yyval = new ParserVal(expr);
 }
break;
case 38:
//#line 263 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    IntegerLiteralExpression expr = new IntegerLiteralExpression((Integer) val_peek(0).obj);
	yyval = new ParserVal(expr);
 }
break;
case 39:
//#line 267 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    StringLiteralExpression expr = new StringLiteralExpression(val_peek(0).sval);
	yyval = new ParserVal(expr);
 }
break;
case 40:
//#line 271 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    BooleanLiteralExpression expr = new BooleanLiteralExpression(true);
    yyval = new ParserVal(expr);
 }
break;
case 41:
//#line 275 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    BooleanLiteralExpression expr = new BooleanLiteralExpression(false);
    yyval = new ParserVal(expr);
 }
break;
case 42:
//#line 279 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    NullLiteralExpression expr = new NullLiteralExpression();
    yyval = new ParserVal(expr);
 }
break;
case 43:
//#line 283 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(1);
 }
break;
case 44:
//#line 286 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
}
break;
          case 45:
//#line 292 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	List<IExpression> elements = (List<IExpression>) val_peek(1).obj;
	ArrayLiteralExpression expr = new ArrayLiteralExpression();
	expr.setElements(elements);
	yyval = new ParserVal(expr);
 }
break;
          case 46:
//#line 298 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	ArrayLiteralExpression expr = new ArrayLiteralExpression();
	yyval = new ParserVal(expr);
 }
break;
          case 47:
//#line 305 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	List<IExpression> arguments = new CommaSeparatedList<>();
 	arguments.add((IExpression)val_peek(0).obj);
 	yyval = new ParserVal(arguments);
 }
break;
          case 48:
//#line 310 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	List<IExpression> arguments = (List<IExpression>) val_peek(2).obj;
	arguments.add((IExpression) val_peek(0).obj);
	yyval = val_peek(2);
 }
break;
          case 49:
//#line 318 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = new UnaryMinus();
 	yyval = new ParserVal(op);
 }
break;
          case 50:
//#line 322 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = new Not();
 	yyval = new ParserVal(op);
 }
break;
          case 51:
//#line 329 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	yyval = val_peek(0);
 }
break;
          case 52:
//#line 332 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	IUnaryOperator op = (IUnaryOperator) val_peek(1).obj;
 	UnaryOperationExpression expr = new UnaryOperationExpression(op, (IExpression)val_peek(0).obj);
 	yyval = new ParserVal(expr);
 }
break;
case 53:
//#line 340 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 54:
//#line 343 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Mul(), val_peek(0));
 }
break;
case 55:
//#line 346 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Div(), val_peek(0));
 }
break;
case 56:
//#line 349 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Modulo(), val_peek(0));
 }
break;
case 57:
//#line 355 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 58:
//#line 358 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Plus(), val_peek(0));
 }
break;
case 59:
//#line 361 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Minus(), val_peek(0));
 }
break;
case 60:
//#line 367 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 61:
//#line 370 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Lt(), val_peek(0));
 }
break;
case 62:
//#line 373 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Gt(), val_peek(0));
 }
break;
case 63:
//#line 376 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Le(), val_peek(0));
 }
break;
case 64:
//#line 379 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Ge(), val_peek(0));
 }
break;
case 65:
//#line 385 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 66:
//#line 388 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Eq(), val_peek(0));
 }
break;
case 67:
//#line 391 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Neq(), val_peek(0));
 }
break;
case 68:
//#line 397 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 69:
//#line 400 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new And(), val_peek(0));
 }
break;
case 70:
//#line 406 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
 }
break;
case 71:
//#line 409 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = constructBinaryOperation(val_peek(2), new Or(), val_peek(0));
 }
break;
case 72:
//#line 415 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
    yyval = val_peek(0);
}
break;
          case 73:
//#line 418 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
          {
              IExpression leftOperand = (IExpression) val_peek(2).obj;
              IExpression rightOperand = (IExpression) val_peek(0).obj;
              AssignmentExpression expr = new AssignmentExpression(leftOperand, rightOperand);
              yyval = new ParserVal(expr);
          }
break;
          case 74:
//#line 424 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
	IExpression leftOperand = new GlobalVariableExpression(val_peek(2).sval);
	IExpression rightOperand = (IExpression) val_peek(0).obj;
	IExpression expr = new AssignmentExpression(leftOperand, rightOperand);
	yyval = new ParserVal(expr);
 }
break;
          case 75:
//#line 433 "/home/amalnev/dev/solarium/src/main/yacc/solarium-2.y"
{
 	yyval = val_peek(0);
 }
break;
//#line 1248 "Parser.java"
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
