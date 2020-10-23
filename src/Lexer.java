import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final String str;
    private int index = 0;

    public Lexer(String str) {
        this.str = str;
    }

    private int match(Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        matcher.region(index, str.length());

        if (matcher.lookingAt()) {
            return matcher.end();
        } else {
            return -1;
        }
    }

    private Token matchNumber() {
        Pattern numberPattern = Pattern.compile("[0-9]+(\\.[0-9]*)?|\\.[0-9]+");
        int matched = match(numberPattern);

        if (matched < 0) {
            return null;
        }

        String numberText = str.substring(index, matched);
        return new Token(TokenType.NUMBER, numberText, index, matched);
    }

    private final Map<String, TokenType> SYMBOL_MAP = new LinkedHashMap<>();
    {
//        SYMBOL_MAP.put("while", TokenType.WHILE);
//        SYMBOL_MAP.put("done", TokenType.DONE);
//        SYMBOL_MAP.put("++", TokenType.INCR);
//        SYMBOL_MAP.put("--", TokenType.DECR);
//        SYMBOL_MAP.put(">=", TokenType.MORE_OR_EQUAL);
//        SYMBOL_MAP.put("<=", TokenType.LESS_OR_EQUAL);
//        SYMBOL_MAP.put("!=", TokenType.NOT_EQUAl);
        SYMBOL_MAP.put("==", TokenType.EQUAL);
//        SYMBOL_MAP.put(">", TokenType.MORE);
//        SYMBOL_MAP.put("<", TokenType.LESS);

//        SYMBOL_MAP.put("T", TokenType.TRUE);
//        SYMBOL_MAP.put("F", TokenType.FALSE);
        SYMBOL_MAP.put("OR", TokenType.OR);
        SYMBOL_MAP.put("XOR", TokenType.XOR);
        SYMBOL_MAP.put("AND", TokenType.AND);
        SYMBOL_MAP.put("NOT", TokenType.NOT);


//        SYMBOL_MAP.put("+", TokenType.ADD);
//        SYMBOL_MAP.put("-", TokenType.SUB);
//        SYMBOL_MAP.put("*", TokenType.MUL);
//        SYMBOL_MAP.put("/", TokenType.DIV);
//        SYMBOL_MAP.put(";", TokenType.SEMICOLON);
//        SYMBOL_MAP.put("!", TokenType.EXCLAM);
        SYMBOL_MAP.put("(", TokenType.LPAR);
        SYMBOL_MAP.put(")", TokenType.RPAR);
//        SYMBOL_MAP.put("{", TokenType.LFIGBR);
//        SYMBOL_MAP.put("}", TokenType.RFIGBR);
        SYMBOL_MAP.put("=", TokenType.ASSIGN);
    }

    private Token matchAnySymbol() {
        for (Map.Entry<String, TokenType> entry: SYMBOL_MAP.entrySet()) {
            String key = entry.getKey();
            TokenType value = entry.getValue();
            Pattern symbolPattern = Pattern.compile(Pattern.quote(key));
            int matched = match(symbolPattern);

            if (matched < 0) {
                continue;
            }

            String symbolText = str.substring(index, matched);
            return new Token(value, symbolText, index, matched);
        }
        return null;
    }

    private Token matchSpaces() {
        int i = index;

        while (i < str.length()) {
            char ch = str.charAt(i);

            if (ch <= ' ') {
                i++;
            } else {
                break;
            }
        }

        if (i > index) {
            String spaces = str.substring(index, i);
            return new Token(TokenType.SPACES, spaces, index, i);
        } else {
            return null;
        }
    }

    private Token matchVariable() {
        Pattern varPattern = Pattern.compile("[A-Za-z]+[\\w]*");
        int matched = match(varPattern);

        if (matched < 0) {
            return null;
        }

        String varText = str.substring(index, matched);
        return new Token(TokenType.VAR, varText, index, matched);
    }

    private Token matchAnyToken() throws ParseExeption {
        if (index >= str.length())
            return null;

        Token spaceToken = matchSpaces();
        if (spaceToken != null)
            return spaceToken;

        Token numberToken = matchNumber();
        if (numberToken != null)
            return numberToken;

        Token symbolToken = matchAnySymbol();
        if (symbolToken != null)
            return symbolToken;

        Token varToken = matchVariable();
        if (varToken != null)
            return varToken;

        throw new ParseExeption(
                "Unexpected character '" + str.charAt(index) + "'", index
        );
    }

    public Token nextToken() throws ParseExeption {
        while (true) {
            Token token = matchAnyToken();

            if (token == null) {
                return null;
            }

            index = token.to;

            if (token.type != TokenType.SPACES) {
                return token;
            }
        }
    }

    public List<Token> getAllTokens() throws ParseExeption {
        List<Token> allTokens = new ArrayList<>();

        while (true) {
            Token token = nextToken();

            if (token == null)
                break;
            allTokens.add(token);
        }
        return allTokens;
    }
}

