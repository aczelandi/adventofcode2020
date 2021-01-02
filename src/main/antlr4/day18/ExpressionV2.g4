grammar ExpressionV2;

expression
   : expression PLUS expression
   | expression TIMES expression
   | parens
   | atom
   ;

parens
   : LPAREN expression RPAREN
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