public enum TokenType {
    SPACES, // пробел
    NUMBER, // число
    ADD, // символ "+"
    SUB, // символ "-"
    MUL, // символ "*"
    DIV, // символ "/"
    EXCLAM, // символ "!"
    SEMICOLON, // символ ";"
    WHILE,
    DONE,
    TRUE,
    FALSE,

    OR,
    XOR,
    AND,
    NOT,


    INCR, // инкремент
    DECR, // декремент
    LPAR, // символ "("
    RPAR, // символ ")"
    LFIGBR, // символ "{"
    RFIGBR, // символ "}"
    VAR, // переменная
    ASSIGN, // приравнивание
    MORE, // больше
    LESS, // меньше
    EQUAL, // равно
    MORE_OR_EQUAL, // больше или равно
    LESS_OR_EQUAL, // меньше или равно
    NOT_EQUAl // не равно
}