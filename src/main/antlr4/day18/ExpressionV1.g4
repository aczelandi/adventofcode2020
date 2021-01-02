grammar ExpressionV1;

expression
   : expression op expression
   | parens
   | atom
   ;

parens
   : LPAREN expression RPAREN
   ;

op
   : (PLUS | TIMES)
   ;

atom
   : SCIENTIFIC_NUMBER
   ;

SCIENTIFIC_NUMBER
   : UNSIGNED_INTEGER
   ;

fragment UNSIGNED_INTEGER
   : ('0' .. '9')+
   ;

LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

PLUS
   : '+'
   ;

TIMES
   : '*'
   ;

WS
   : [ \r\n\t] + -> skip
   ;