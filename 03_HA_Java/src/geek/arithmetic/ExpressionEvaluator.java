package geek.arithmetic;

public interface ExpressionEvaluator {
    Tuple2<Double, ExpressionError> EvaluateExpression(String expression);
}
