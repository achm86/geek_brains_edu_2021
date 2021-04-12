package geek.arithmetic;

import java.util.HashMap;

public class EvaluatorsFactory {

    public enum ExpressionPrecision {Integer, Double, Decimal}

    // NOTE: this collection will hold evaluators instances till the end of time,
    // holding all allocated resources. This is done by design assuming that:
    //   * performance is the critical point of the system
    //   * memory consumption is not
    private static HashMap<ExpressionPrecision, ExpressionEvaluator> evaluators = new HashMap<>();

    public static ExpressionEvaluator getEvaluator(ExpressionPrecision type) {
        synchronized (evaluators) {
            ExpressionEvaluator evaluator = null;
            if (!evaluators.containsKey(type)) {
                // lazy init for evaluators instances
                if (type == ExpressionPrecision.Integer) {
                    evaluator = new IntegerEvaluator();
                } else {
                    throw new java.lang.UnsupportedOperationException("Operation type " + type.toString() + " is not supported");
                }
                evaluators.put(type, evaluator);
            }
            return evaluators.get(type);
        }
    }

}
