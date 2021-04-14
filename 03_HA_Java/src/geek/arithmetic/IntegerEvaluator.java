package geek.arithmetic;

import java.util.Vector;

class IntegerEvaluator extends ExpressionEvaluator {

    protected Tuple2<Double, ExpressionError> evaluateRPN(Vector<ExpressionToken> tokens) {
        Vector<Integer> values = new Vector<>();
        for(int i = 0; i < tokens.size(); ++i) {
            if (tokens.elementAt(i).getType() == ExpressionToken.ExpressionType.Operation) {
                if (values.size() < 2)
                    return new Tuple2<>(null, new ExpressionError("incorrect expression format"));

                // get both parts of operation and apply it
                int lhs = values.elementAt(values.size() - 2);
                int rhs = values.elementAt(values.size() - 1);
                Tuple2<Integer, ExpressionError> result = ((ExpressionOperation)tokens.elementAt(i)).applyOperation(lhs, rhs);

                // check operation error if any
                if (result.getError() != null)
                    return new Tuple2<>(null, result.getError());

                // remove lhs and rhs from values stack
                for (int j = 0; j < 2; ++j)
                    values.remove(values.size() - 1);

                // add op(lhs, rhs) to the stack
                values.add(result.getValue());
            } else if (tokens.elementAt(i).getType() == ExpressionToken.ExpressionType.Value) {
                ExpressionValue value = (ExpressionValue)tokens.elementAt(i);
                int intValue = Integer.valueOf(value.getValue());
                values.add(intValue);
            }
        }

        if (values.size() != 1)
            return new Tuple2<>(null, new ExpressionError("incorrect expression format"));

        return new Tuple2<>(1.0 * values.elementAt(0), null);
    }
}
