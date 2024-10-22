package edu.psu.ist.monkey.lang;

public sealed interface Token {

    default String display() {
        return switch (this) {
            case Illegal _ -> "Illegal";
            case Eof _ -> "Eof";
            //
            case Ident(var id) -> id;
            case Int(var i) -> i + "";
            //
            case Assign _ -> "=";
            case Plus _ -> "+";
            //
            case Comma _ -> ",";
            case Semi _ -> ";";
            //
            case LParen _ -> "(";
            case RParen _ -> ")";
            //
            case LBrace _ -> "{";
            case RBrace _ -> "}";
            //
            case Fun _ -> "fun";
            case Let _ -> "let";
            case Err _ -> "err";
        };
    }

    enum Illegal implements Token {IllegalInst}
    enum Eof implements Token {EofInst}
    enum Err implements Token {ErrorInst}
    // identifiers + literals
    record Ident(String id) implements Token {}
    record Int(int i) implements Token {}

    // operators
    enum Assign implements Token {AssignInst}
    enum Plus implements Token {PlusInst}

    // delimiters
    enum Comma implements Token {CommaInst}
    enum Semi implements Token {SemiInst}
    
    enum LParen implements Token {LParenInst}
    enum RParen implements Token {RParenInst}
    enum LBrace implements Token {LBraceInst}
    enum RBrace implements Token {RBraceInst}
    
    // keywords
    enum Fun implements Token {FunInst}
    enum Let implements Token {LetInst}
}
