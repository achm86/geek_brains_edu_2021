package geek.arithmetic;

public class ExpressionValue implements ExpressionToken {
    ExpressionValue(String val) {
        value = val;
    }
    @Override
    public ExpressionType getType() {
        return ExpressionType.Value;
    }
    @Override
    public String toString() {
        return value;
    }
    public String getValue() {
        return value;
    }
    private String value;
}
