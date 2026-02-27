import math
import time
from lexer import *
from grammar import *
from enum import Enum
from parser import *
from interpret import *

program= """\

LET fac =   
FUNC n 
{
    IF n == 1 { 
        RETURN 1; 
    } ELSE {
        RETURN n * CALL(fac,n-1);
    };
};

LET x = CALL(fac,6);

PRINT x;

"""


tokens = tokenizer(program)
root = generateParseTree(tokens)


class Type(Enum):
    INT = 0,
    FLOAT = 2,
    BOOL = 3,
    VOID = 4

class ANTNode:
    def __init__(self, type):
        self.type = type


print(f"Valid Syntax:\n{program}\nEvaluation:")
env = {}
env["PRINT"] = lambda x : print(x)
env["VOID"] = lambda x : None
env["WAIT"] = lambda x : time.sleep(x)
env[SCOPE] = 0

print(f"\nExit: {eval(root, 0, env)}")