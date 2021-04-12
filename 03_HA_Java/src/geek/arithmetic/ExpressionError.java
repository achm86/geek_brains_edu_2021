package geek.arithmetic;

public class ExpressionError {
    private String error;
    ExpressionError(String err) {
        error = err;
    }
    String getValue() {
        return error;
    }
    boolean noError() {
        return error == null;
    }
}
