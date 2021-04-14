package geek.arithmetic;

public interface ExpressionToken {
    enum ExpressionType {Value, Operation}
    ExpressionType getType();
    String toString();
}
