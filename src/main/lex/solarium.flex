package ru.amalnev.solarium.language;

%%

%class Lexer
%line
%column
%integer

%{
    private Parser yyparser;

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
StringLiteral   = \"([^\"])*\"
InputCharacter  = [^\r\n]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

%%

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
{StringLiteral} {
		          String value = yytext();
		          value = value.replaceAll("^\"", "");
		          value = value.replaceAll("\"$", "");
		          yyparser.yylval = new ParserVal(value);
		          return Parser.STRING_LITERAL;
                }

