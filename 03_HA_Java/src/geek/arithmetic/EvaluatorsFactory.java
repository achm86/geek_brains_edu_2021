package geek.arithmetic;

import java.util.HashMap;

public class EvaluatorsFactory {

    public enum ExpressionType {Integer, Double, Decimal}

    // NOTE: this collection will hold evaluators instances till the end of time,
    // holding all allocated resources. This is done by design assuming that:
    //   * performance is the critical point of the system
    //   * memory consumption is not
    private static HashMap<EvaluatorsFactory.ExpressionType, ExpressionEvaluator> evaluators;

    public static ExpressionEvaluator getEvaluator(EvaluatorsFactory.ExpressionType type) {
        synchronized (evaluators) {
            if (evaluators == null) {
                // lazy init for evaluators collection
                evaluators = new HashMap<>();
            }
            ExpressionEvaluator evaluator = null;
            if (!evaluators.containsKey(type)) {
                // lazy init for evaluators instances
                if (type == EvaluatorsFactory.ExpressionType.Integer) {
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
