grammar Snafu;

@header {
package foo.bar.baz;
}

startRule   : (statement EOL)* ;
statement   : term ( (ADD | SUB) term )*
term        : factor ( (MUL | DIV ) factor )* ;
factor      : constant | LPAREN statement RPAREN ;
constant    : INTEGER | FLOAT ;

EOL     : ';' ;
ADD     : '+' ;
SUB     : '-' ;
MUL     : '*' ;
DIV     : '/' ;
LPAREN  : '(' ;
RPAREN  : ')' ;

INTEGER : SIGN? DIGIT+ ;
FLOAT   : SIGN? (DIGIT)+ DOT (DIGIT)* EXPONENT?
        | SIGN? DOT (DIGIT)+ EXPONENT?
        | SIGN? (DIGIT)+ EXPONENT ;
fragment
EXPONENT: ('e'|'E') SIGN? ? DIGIT+ ;

SIGN    : '+' | '-' ;
DIGIT   : [0-9] ;

WS      : [ \t\u000C\n]+ -> skip ;
