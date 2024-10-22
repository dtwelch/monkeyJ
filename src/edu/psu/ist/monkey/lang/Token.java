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
        };
    }

    enum Illegal implements Token {IllegalInst}
    enum Eof implements Token {EofInst}

    // identifiers + literals
    record Ident(String id) implements Token {}
    record Int(int i) implements Token {}

    // operators
    enum Assign implements Token {}
    enum Plus implements Token {}

    // delimiters
    enum Comma implements Token {}
    enum Semi implements Token {}
    
    enum LParen implements Token {}
    enum RParen implements Token {}
    enum LBrace implements Token {}
    enum RBrace implements Token {}
    
    // keywords
    enum Fun implements Token {}
    enum Let implements Token {}
}
