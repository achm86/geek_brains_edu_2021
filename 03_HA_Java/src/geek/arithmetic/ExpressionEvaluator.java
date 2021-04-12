package geek.arithmetic;

public interface ExpressionEvaluator {
    public enum EvaluatorErrorType {NoError, Format, Ca}
    public Tuple2<Double, ExpressionError> EvaluateExpression(String expression);
}
