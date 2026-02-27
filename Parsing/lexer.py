from grammar import *

DELIMITERS = " \t\n"
class Token:
    def __init__(self, str, value):
        self.str = str
        self.value = value


def tokenizer(s):

    tokens = []

    N = len(s)
    i = 0
    while i < N:
        c = s[i]
        if c in DELIMITERS:
            i += 1
        elif c == ';':
            while i < N and s[i] == ';':
                i += 1
            tokens.append(Token("end", 0))
        elif c.isdigit():
            val = 0.0
            while i < N and s[i].isdigit():
                val *= 10
                val += int(s[i])
                i += 1
            if i < N:
                if s[i] == ".":
                    i += 1
                dec = 0.1
                while i < N and s[i].isdigit():
                    val += dec * int(s[i])
                    dec *= 0.1
                    i += 1
            tokens.append(Token("literal", val))

        elif str(c) in LITERAL_TERMS:
            i += 1
            if c == '=':
                if i < N and s[i] == '=':
                    i += 1
                    tokens.append(Token("==", 0))
                else:
                    tokens.append(Token("=", 0))
            else:
                tokens.append(Token(str(c), 0))
        elif c.isalpha():
            var = []
            while i < N and s[i].isalpha():
                var.append(s[i])
                i += 1
            toStr = ''.join(var)

            if toStr == "T":
                tokens.append(Token("literal", True))
            elif toStr == "F":
                tokens.append(Token("literal", False))
            elif toStr in LITERAL_TERMS:
                tokens.append(Token(toStr, 0))
            elif toStr == toStr.lower():
                tokens.append(Token("var", toStr))
            elif toStr == toStr.upper():
                tokens.append(Token("cmd", toStr))
            else:
                print(f"Invalid Token: {toStr}")
                quit()
    tokens.append(Token("$", 0))
    return tokens

def matchToken(type, str):
    if type == "literal":
        try:
            int(str)
            return True
        except:
            return False
    elif type in TERMINALS:
        return True
    return False
