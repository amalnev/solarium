package ru.amalnev.solarium.language;

%%

%class Lexer
%line
%column
%integer

%{
    private Parser yyparser;

    private StringBuilder stringLiteral = new StringBuilder();

    public Lexer(java.io.Reader r, Parser yyparser)
    {
        this(r);
        this.yyparser = yyparser;
    }

    public int getLineNumber()
    {
        return yyline;
    }
%}

LineTerminator  = \r|\n|\r\n
WhiteSpace      = {LineTerminator} | [ \t\f]
Identifier      = [:jletter:][:jletterdigit:]*
InputCharacter  = [^\r\n]

/* comments */
Comment = {TraditionalComment} |  {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

%state STRING_LITERAL

%%

<YYINITIAL>
{
    /* comments */
    {Comment}       { /* ignore */ }
    {WhiteSpace}    { /* ignore */ }

    "null"    		{ return Parser.NULL; }
    "if"      		{ return Parser.IF; }
    "else"    		{ return Parser.ELSE; }
    "while"   		{ return Parser.WHILE; }
    "do"      		{ return Parser.DO; }
    "for"     		{ return Parser.FOR; }
    "continue" 		{ return Parser.CONTINUE; }
    "break"   		{ return Parser.BREAK; }
    "return"  		{ return Parser.RETURN; }
    "function" 		{ return Parser.FUNCTION; }
    "true"          { return Parser.TRUE; }
    "false"         { return Parser.FALSE; }
    "global"        { return Parser.GLOBAL; }
    {Identifier} 	{ yyparser.yylval = new ParserVal(yytext()); return Parser.IDENTIFIER;}
    ";"    			{ return Parser.SEMICOLON; }
    ":"    			{ return Parser.COLON; }
    "<="   			{ return Parser.LE_OP; }
    ">="   			{ return Parser.GE_OP; }
    "=="   			{ return Parser.EQ_OP; }
    "!="   			{ return Parser.NE_OP; }
    "&&"   			{ return Parser.AND_OP; }
    "||"   			{ return Parser.OR_OP; }
    "["    			{ return Parser.OPEN_SQUARE_BRACKET; }
    "]"    			{ return Parser.CLOSE_SQUARE_BRACKET; }
    "("    			{ return Parser.OPEN_BRACKET; }
    ")"    			{ return Parser.CLOSE_BRACKET; }
    ","    			{ return Parser.COMMA; }
    "-"    			{ return Parser.MINUS; }
    "!"    			{ return Parser.EXCLAMATION_MARK; }
    "+"    			{ return Parser.PLUS; }
    "*"    			{ return Parser.MUL; }
    "/"    			{ return Parser.DIV; }
    "%"      		{ return Parser.MODULO; }
    "<"    			{ return Parser.LT; }
    ">"    			{ return Parser.GT; }
    "="    			{ return Parser.EQ; }
    "."    			{ return Parser.DOT; }
    "{"    			{ return Parser.OPEN_CURLY_BRACKET; }
    "}"    			{ return Parser.CLOSE_CURLY_BRACKET; }
    [+-]*[0-9]+ 	{
                      yyparser.yylval = new ParserVal(Integer.valueOf(yytext()));
                      return Parser.NUMERIC_LITERAL;
                    }
    \"              { yybegin(STRING_LITERAL); stringLiteral.setLength(0); }
}

<STRING_LITERAL>
{
    \"              {
                        yybegin(YYINITIAL);
                        yyparser.yylval = new ParserVal(stringLiteral.toString());
                        return Parser.STRING_LITERAL;
                    }
    [^\n\r\"\\]+    { stringLiteral.append( yytext() ); }
    \\t             { stringLiteral.append('\t'); }
    \\n             { stringLiteral.append('\n'); }
    \\r             { stringLiteral.append('\r'); }
    \\\"            { stringLiteral.append('\"'); }
    \\              { stringLiteral.append('\\'); }
}

