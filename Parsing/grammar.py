LITERAL_TERMS = ["=", "==", "!", "&", "|", "<", ">", "{", "}", "+", 
                 "-","*","/", "^", ")", ",","(", ".", "IF", "ELSE", "ELIF", "FUNC",
                 "CALL", "RETURN", "LET"]

SPECIAL_TERMS = ["", "$", "end", "literal", "var", "cmd", "T", "F"] 

TERMINALS = LITERAL_TERMS + SPECIAL_TERMS



start = "program"
RULES = {
    "program" : [["stmt" , "programP"]],
    "programP" : [["stmt", "programP"], [""]],
    "stmt" : [["LET", "var", "=", "expr", "end"], ["var", "=", "expr", "end"], ["cmd", "args", "end"], ["body", "end"], 
                    ["ifstmt"], ["RETURN", "expr", "end"]],
    "funcdef" : [["FUNC", "varlist", "body"]],
    "varlist" :[["var", "varlist"], [""]],
    "ifstmt" : [["IF", "expr", "body", "elifstmt"]],
    "elifstmt" : [["ELIF", "expr", "body", "elifstmt"], ["ELSE", "body", "end"], ["end"]],
    "body" : [["{",  "program", "}"]],
    "expr" : [["bexpr", "exprP"], ["!", "bexpr", "exprP"], ["funcdef"]],
    "exprP" :[["&", "bexpr", "exprP"], ["|", "bexpr", "exprP"], [""]],
    "bexpr" : [["nexpr", "bexprP"]],
    "bexprP" : [["<", "nexpr"], [">", "nexpr"], ["==", "nexpr"], [""]],
    "nexpr" : [["term", "nexprP"], ["-", "term", "nexprP"]],
    "nexprP": [["+", "term", "nexprP"], ["-", "term", "nexprP"], [""]],
    "term" : [["fact", "termP"]],
    "termP": [["*", "fact", "termP"], ["/", "fact", "termP"], [""]],
    "fact" : [["paren", "factP"]],
    "factP": [["^", "paren", "factP"], [""]],
    "paren" : [["(", "expr", ")"], ["literal"], ["var"], ["CALL", "(", "args",")"]],
    "args" :[["expr", "argsP"]],
    "argsP" :[[",", "expr", "argsP"], [""]],
}