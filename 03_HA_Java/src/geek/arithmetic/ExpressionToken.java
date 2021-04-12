package geek.arithmetic;

public interface ExpressionToken {
    public enum ExpressionType {Value, Operation}
    ExpressionType getType();
    String toString();
}
