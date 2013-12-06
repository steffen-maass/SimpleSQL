grammar SimpleSQL;

@header
{
    package SimpleSQL;
}

// PARSER
statement : SELECT S projections S FROM S relations S (WHERE S selections)? (';')?;

projections : '*' | projectionFragment ((S)? COMMA (S)? projectionFragment)*;
projectionFragment : qualifiedField;

relations : relationFragment ((S)? COMMA (S)? relationFragment)*;
relationFragment : IDENTIFIER;

selections : selection (S AND S selection)*;
selection : fieldComparison | joinStatement;
fieldComparison : fieldIdentifier (S)? comparison (S)? selectionFragment;
joinStatement : fieldIdentifier (S)? comparison (S)? fieldIdentifier;
fieldIdentifier : qualifiedField;
selectionFragment : STRING;
comparison : ('=' | '>' | '>=' | '<=' | '<') ;

qualifiedField : tablePrefix '.' tableField;
tablePrefix : IDENTIFIER;
tableField : IDENTIFIER;

// LEXER
SELECT : ('select' | 'Select' | 'SELECT');
FROM : ('from' | 'From' | 'FROM');
WHERE : ('where' | 'Where' | 'WHERE');
AND : ('and' | 'And' | 'AND');
COMMA : ',';
STRING : '"' ('a'..'z'|'A'..'Z'|'0'..'9'|' '|'-'|'_')+ '"';
IDENTIFIER : ('a'..'z'|'A'..'Z'|'0'..'9'|'_')+;
// S = spaces and separators
S : (' ' | '\r' '\n' | '\n' | '\r')+;