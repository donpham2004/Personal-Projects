import math
from grammar import *
from parser import *

EVAL_TABLE = {}


RETURN = "RETURN"
SCOPE = "SCOPE"
RETURN_VALUE = "RETURN_VALUE"

CALL_STACK = [[False, 0]]

ENV_STACK = []

def eval(self, value, env):
    if CALL_STACK[-1][0]:
        return 0
    return EVAL_TABLE[self.type](self, value, env)

def args_eval(self, value, env):
    # "args" :[["expr", "argsP"]],
    expr = eval(self.nodes[0],0, env)
    return [expr] + eval(self.nodes[1],0, env)

def argsP_eval(self, value, env):
    # "argsP" :[[",", "expr", "argsP"], [""]],
    if self.rule[0] == ",":
        expr = eval(self.nodes[1],0, env)
        return [expr] + eval(self.nodes[2],0, env)
    else:
        return []

def paren_eval(self, value, env):
    # "paren" : [["(", "expr", ")"], ["literal"], ["var"], ["CALL", "args"]]
    if self.rule[0] == "literal":
        return self.nodes[0].token.value
    elif self.rule[0] == "var": 
        var = self.nodes[0].token.value

        if not var in env.keys():
            print(f"Undefined variable {var}")
            quit()
        else:
            return env[var][-1]
    elif self.rule[0] == "(": #(expr)
        return eval(self.nodes[1], 0, env)
    elif self.rule[0] == "CALL":
        #["CALL", "(","args",")"]
        args = eval(self.nodes[2],0, env)
        func = args[0]
        args = args[1:]
        param, body = func[0], func[1]

        new = copy.deepcopy(env) 
        CALL_STACK.append([False, None])
        for i in range(0, len(param)):
            if not param[i] in new.keys():
                new[param[i]] = []
            new[param[i]].append(args[i])
        eval(body,0, new)
        return CALL_STACK.pop()[1]

def fact_eval(self, value, env):
    # "fact" : [["paren", "factP"]],
    paren = eval(self.nodes[0],0,env)
    return eval(self.nodes[1],paren,env)

def factP_eval(self, value, env):
    # "factP": [["^", "paren", "factP"], [""]],
    if self.rule[0] == "^":
        paren = self.nodes[1].eval(0,env)
        return math.pow(value, (self.nodes[2],paren,env))
    else:
        return value

def termP_eval(self, value, env):
    # "termP": [["*", "fact", "termP"], ["/", "fact", "termP"], [""]],
    if self.rule[0] == "*":
        fact = eval(self.nodes[1],0,env)
        return  eval(self.nodes[2],value * fact,env)
    elif self.rule[0] == "/":
        fact = eval(self.nodes[1],0,env)
        if fact == 0:
            print("Division By Zero")
            quit()
        return  eval(self.nodes[2],value / fact,env)
    else:
        return value

def term_eval(self, value, env):
    # "term" : ["fact", "termP"]
    fact = eval(self.nodes[0],0,env)
    return eval(self.nodes[1],fact,env)

def nexprP_eval(self, value, env):
    # "nexprP": [["+", "term", "nexprP"], ["-", "term", "nexprP"], [""]],
    if self.rule[0] == "+":
        term = eval(self.nodes[1],0,env)
        return eval(self.nodes[2],value + term,env)
    elif self.rule[0] == "-":
        term = eval(self.nodes[1],0,env) 
        return  eval(self.nodes[2],value - term,env)
    else:
        return value

def nexpr_eval(self, value, env):
    # "nexpr" : [["term", "nexprP"], ["-", "term", "nexprP"]],
    if self.rule[0] == "-":
        term = eval(self.nodes[1],0,env)
        return eval(self.nodes[2],-term,env)
    else:
        term = eval(self.nodes[0],0,env)
        return eval(self.nodes[1],term,env)

def bexpr_eval(self, value, env):
    # "bexpr" : [["nexpr", "bexprP"]],
    nexpr = eval(self.nodes[0],0, env)
    return eval(self.nodes[1],nexpr, env)

def bexprP_eval(self, value, env):
    # "bexprP" : [["<", "nexpr"], [">", "nexpr"], ["==", "nexpr"], [""]],
    if self.rule[0] == "<":
        return value < eval(self.nodes[1],value, env)
    elif self.rule[0] == ">":
        return value > eval(self.nodes[1],value, env)
    elif self.rule[0] == "==":
        return value == eval(self.nodes[1],value, env)
    else:
        return value

def expr_eval(self, value, env):
    # "expr" : [["bexpr", "exprP"], ["!", "bexpr", "exprP"], ["funcdef"],
    if self.rule[0] == "bexpr":
        bexpr = eval(self.nodes[0],0, env)
        return eval(self.nodes[1],bexpr, env)
    elif self.rule[0] == "!":
        bexpr = eval(self.nodes[1],0, env)
        return eval(self.nodes[2],not bexpr, env)
    elif self.rule[0] == "funcdef":
        return eval(self.nodes[0],0, env)

def exprP_eval(self, value, env):
    # "exprP" :[["&", "bexpr", "exprP"], ["|", "bexpr", "exprP"], [""]],
    if self.rule[0] == "&":
        bexpr = eval(self.nodes[1],0, env)
        return eval(self.nodes[2],value and bexpr, env)
    elif self.rule[0] == "|":
        bexpr = eval(self.nodes[1],0, env)
        return eval(self.nodes[2],value or bexpr, env)
    else:
        return value

import copy
def body_eval(self, value, env):
    # "body" : [["{", "program", "}", "end"]],
    new = copy.deepcopy(env)
    new[SCOPE] = env[SCOPE] + 1
    eval(self.nodes[1],0, new)
    for key in env.keys():
        if type(env[key]) == list:
            i = len(env[key]) - 1
            env[key][i] = new[key][i]
    return 0

def ifstmt_eval(self, value , env):
    # "ifstmt" : [["IF", "expr", "body", "elifstmt"]],
    expr = eval(self.nodes[1],0,env)
    if expr:
        return eval(self.nodes[2],0,env)
    else:
        return eval(self.nodes[3],0, env)

def elifstmt_eval(self, value , env):
    # ["ELIF", "expr", "body", "elifstmt"]
    if self.rule[0] == "ELIF":
        expr = eval(self.nodes[1],0,env)
        if expr:
            return eval(self.nodes[2],0,env)
        else:
            return eval(self.nodes[3],0, env)
    elif self.rule[0] == "ELSE": #["ELSE", "body", "end"]
        return eval(self.nodes[1],0, env)
    else: #["end"]
        return value

def varlist_eval(self, value, env):
    # "varlist" ["var", "varlist"], [""]
    if self.rule[0] == "var":
        var = self.nodes[0].token.value
        return [var] + eval(self.nodes[1],[var], env)
    return []

def varlistP_eval(self, value, env):
    # "varlistP" :[[",", "var", "varlistP"], [""]],
    if self.rule[0] == ",":
        var = self.nodes[1].token.value
        return [var] + eval(self.nodes[2],0, env)
    else:
        return []

def funcdef_eval(self, value, env):
    # "funcdef" : [["FUNC", "varlist", "body", "end"]],
    varlist = eval(self.nodes[1],0, env)
    body = self.nodes[2]
    return [varlist,body]

def stmt_eval(self, value, env):
    # ["LET", "var", "=", "expr", "end"]
    if self.rule[0] == "LET":
        expr = eval(self.nodes[3], 0, env)
        var = self.nodes[1].token.value 
        if var in env.keys() and env[SCOPE] == len(env[var]) - 1:
            print(f"Redeclaration of {var}")
            quit()
        if not var in env.keys():
            env[var] =[]
        env[var].append(expr)
    elif self.rule[0] == "var":
        expr = eval(self.nodes[2], 0, env)
        var = self.nodes[0].token.value 
        if not var in env.keys():
            print(f"Undefined variable {var}")
            quit()
        env[var][-1] = expr
    elif self.rule[0] == "cmd":  # ["cmd", "args", "end"]
        args = eval(self.nodes[1],0, env)
        cmd = self.nodes[0].token.value
        if not cmd in env.keys():
            print(f"Unknown command: {cmd}")
            quit()
        try:
            return env[cmd](*args)
        except:
            print(f"Command Failed")
            quit()
    elif self.rule[0] == "body":  # ["body"]
        return eval(self.nodes[0],0, env)
    elif self.rule[0] == "ifstmt":  
        # ["ifstmt]"
        return eval(self.nodes[0],0, env)
    elif self.rule[0] == "RETURN":
        # ["RETURN", "expr", "end"]],
        expr = eval(self.nodes[1],0,env)
        CALL_STACK[-1] = [True, expr]
        return expr


def program_eval(self, value, env):
    # "program" : [["stmt" , "programP"]],
    stmt = eval(self.nodes[0],0,env)
    eval(self.nodes[1],stmt,env)
    return CALL_STACK[0][1]

def programP_eval(self, value, env):
    # "programP" : [["stmt", "programP"], [""]],
    if self.rule[0] == "stmt":
        stmt = eval(self.nodes[0],0,env)
        eval(self.nodes[1],stmt,env)
    return value

EVAL_TABLE = {
    "program":program_eval,
    "programP":programP_eval,
    "stmt": stmt_eval,
    "funcdef":funcdef_eval,
    "varlist":varlist_eval,
    "ifstmt" : ifstmt_eval,
    "elifstmt": elifstmt_eval,
    "body" : body_eval,
    "expr" : expr_eval,
    "exprP" : exprP_eval,
    "bexpr": bexpr_eval,
    "bexprP" : bexprP_eval,
    "nexpr" : nexpr_eval,
    "nexprP" : nexprP_eval,
    "term" : term_eval,
    "termP" : termP_eval,
    "fact" : fact_eval,
    "factP" : factP_eval,
    "paren" : paren_eval,
    "args" : args_eval,
    "argsP" : argsP_eval
}

assert(EVAL_TABLE.keys() == RULES.keys())