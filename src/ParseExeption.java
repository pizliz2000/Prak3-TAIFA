public class ParseExeption extends Exception {
    public final int index;
    public ParseExeption(String message, int index) {
        super(message);
        this.index = index;
    }
}


