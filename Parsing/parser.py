from grammar import *
FIRST = {}

def calcFirst(type):
    if type in FIRST.keys():
        return
    elif type in TERMINALS:
        FIRST[type] = [type]
    elif type in RULES.keys():
        FIRST[type] = []
        for rule in RULES[type]:
            for term in rule:
                if not term in FIRST.keys():
                    calcFirst(term)
                FIRST[type] += FIRST[term]
                if not "" in FIRST[term]:
                    break
            else:
                FIRST[type] += ""
        FIRST[type] = list(set(FIRST[type]))


FOLLOW = {}

def getFirst(B):
    first = []
    for b in B:
        first += FIRST[b]
        if not "" in FIRST[b]:
            break
    else:
        first.append("")
    return first


def calcFollow(type):
    if type in FOLLOW.keys():
        return
    elif not type in RULES.keys():
        return

    FOLLOW[type] = []
    if type == start:
        FOLLOW[type].append("$")
    
    for term in RULES.keys():
        for rule in RULES[term]:
            # term : r1 r2 ... rN
            N = len(rule)
            for i in range(N):
                if type == rule[i]: 
                    if i == N-1: # term : a ... type
                        calcFollow(term)
                        FOLLOW[type] += FOLLOW[term]
                    else:
                        # term : a type b
                        tmp = getFirst(rule[i+1:])
                        if "" in tmp:
                            while "" in tmp:
                                tmp.remove("")
                            calcFollow(term)
                            FOLLOW[type] += FOLLOW[term]
                        FOLLOW[type] += tmp

    FOLLOW[type] = list(set(FOLLOW[type]))

                        
class ParseNode:
    def __init__(self, type, rule, token):
        self.nodes = {}
        self.type = type
        self.rule = rule
        self.token = token
    
    def setNodes(self,nodes):
        self.nodes = nodes

            
def generateParsingTable():
    for t in TERMINALS:
        calcFirst(t)

    for t in RULES.keys():
        calcFirst(t)

    for t in RULES.keys():
        calcFollow(t)
    M = {}
    for term in RULES.keys():
        M[term] = {}
        for rule in RULES[term]:
            first = getFirst(rule)
            for t in first:
                if t != "":
                    if t in M[term].keys():
                        print("Grammar is not LL1")
                        quit()
                    M[term][t] = rule
            if "" in first:
                for b in FOLLOW[term]:
                    if b in M[term].keys():
                        print("Grammar is not LL1")
                        quit()
                    M[term][b] = rule
    return M


def generateParseTree(tokens):

    M = generateParsingTable()

    root = ParseNode(start, [], None)
    stack = [ParseNode("$", [], None), root]

    N = len(tokens)
    i = 0

    while stack:
        if i == N:
            print(f"Incomplete string")
            quit()

        token = tokens[i]
        top = stack[-1]
        if top.type == token.str:
            node = stack.pop()
            node.token = token
            i += 1
        elif top.type in TERMINALS:
            print(f"Expected terminal {top.type}")
            quit()
        elif token.str in M[top.type].keys():
            node = stack.pop()
            node.rule = M[top.type][token.str]
            arr = []
            for t in reversed(M[top.type][token.str]):
                new = ParseNode(t, [], None)
                arr.append(new)
                if t != "":
                    stack.append(new)
            arr.reverse()
            node.setNodes(arr)
        elif token.str in TERMINALS:
            print(f"Not expected {token.str}")
            quit()
        else:
            print(f"Unknown terminal {token.str}")
            quit()

    return root



# print("FIRST")
# for k, v in FIRST.items():
#     if k in RULES.keys():
#         print(f"\t{k} : {v}")
        
# print("\nFOLLOW")
# for k, v in FOLLOW.items():
#     print(f"\t{k} : {v}")
# print("\nParsing Table")
# for k, v in M.items():
#     print(f"\t{k} : {v}")