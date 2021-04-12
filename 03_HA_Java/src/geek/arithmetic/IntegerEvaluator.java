package geek.arithmetic;

import java.util.Vector;

class IntegerEvaluator implements ExpressionEvaluator {

    @Override
    public Tuple2<Double, ExpressionError> EvaluateExpression(String expression) {
        Tuple2<Vector<ExpressionToken>, ExpressionError> rpn = constructRPN(expression);
        if (rpn.getError() != null)
            return new Tuple2<>(0.0, rpn.getError());

        // print out RPN string value of the expression
        String expressionRPN = "";
        for(int i = 0; i < rpn.getValue().size(); ++i)
            expressionRPN += rpn.getValue().elementAt(i).toString() + " ";

        System.out.println("Expression RPN is : '" + expressionRPN + "'");
        return evaluateRPN(rpn.getValue());
    }

    private Tuple2<Double, ExpressionError> evaluateRPN(Vector<ExpressionToken> tokens) {
        Vector<Integer> values = new Vector<>();
        for(int i = 0; i < tokens.size(); ++i) {
            if (tokens.elementAt(i).getType() == ExpressionToken.ExpressionType.Operation) {
                if (values.size() < 2)
                    return new Tuple2<>(0.0, new ExpressionError("incorrect expression format"));

                // get both parts of operation and apply it
                int lhs = values.elementAt(values.size() - 2);
                int rhs = values.elementAt(values.size() - 1);
                Tuple2<Integer, ExpressionError> result = ExpressionOperation.applyOperation(lhs, rhs, (ExpressionOperation)tokens.elementAt(i));

                // check operation error if any
                if (result.getError() != null)
                    return new Tuple2<>(0.0, result.getError());

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
            return new Tuple2<>(0.0, new ExpressionError("incorrect expression format"));

        return new Tuple2<>(1.0 * values.elementAt(0), null);
    }

    private Tuple2<Vector<ExpressionToken>, ExpressionError> constructRPN(String expression) {
        Vector<ExpressionToken> tokens = new Vector<>();
        Vector<Character> operationStack = new Vector<>();
        String number = "";

        for(int i = 0; i < expression.length(); ++i) {
            if (isDigit(expression.charAt(i))) {
                number += expression.charAt(i);
            } else {
                if (number.length() > 0) {
                    tokens.add(new ExpressionValue(number));
                    number = "";
                }
                if (expression.charAt(i) == '(') {
                    operationStack.add(expression.charAt(i));
                } else if (expression.charAt(i) == ')') {
                    boolean correct = false;
                    // remove all operations till this '('
                    for(int j = operationStack.size() - 1; j >= 0; --j) {
                        if (operationStack.elementAt(j) == '(') {
                            operationStack.remove(j);
                            correct = true;
                            break;
                        }
                        String op = ""; op += operationStack.elementAt(j);
                        tokens.add(new ExpressionOperation(op));
                        operationStack.remove(j);
                    }
                    if (!correct) {
                        return new Tuple2<>(null, new ExpressionError("input string had incorrect format"));
                    }
                } else if (expression.charAt(i) == ' ') {
                    // just skip spaces
                } else {
                    if (!ExpressionOperation.isOperation(expression.charAt(i))) {
                        return new Tuple2<>(null, new ExpressionError("input string had incorrect format : invalid operation"));
                    }
                    int thisPriority = getOperationPriority(expression.charAt(i));
                    for(int j = operationStack.size() - 1; j >= 0 && operationStack.elementAt(j) != '('; --j) {
                        if (thisPriority >= getOperationPriority(operationStack.elementAt(j))) {
                            String op = ""; op += operationStack.elementAt(j);
                            tokens.add(new ExpressionOperation(op));
                            operationStack.remove(j);
                        } else {
                            break;
                        }
                    }
                    operationStack.add(expression.charAt(i));
                }
            }
        }

        if (number.length() > 0) {
            tokens.add(new ExpressionValue(number));
            number = "";
        }

        for(int j = operationStack.size()-1; j >= 0; --j) {
            String op = "";
            op += operationStack.elementAt(j);
            tokens.add(new ExpressionOperation(op));
        }

        return new Tuple2<>(tokens, null);
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static int getOperationPriority(Character op) {
        if (op == '+' || op == '-') {
            return 3;
        }
        if (op == '/' || op == '*') {
            return 2;
        }
        if (op == '(' || op == ')') {
            return 0;
        }
        return -1;
    }
}
