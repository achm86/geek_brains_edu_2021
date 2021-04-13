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

    private Tuple2<Vector<ExpressionToken>, ExpressionError> constructRPN(String expression) {
        Vector<ExpressionToken> tokens = new Vector<>();
        Vector<Character> operationStack = new Vector<>();
        String number = "";

        // remove spaces in input
        expression = expression.replaceAll("\\s+","");

        for(int i = 0; i < expression.length(); ++i) {
            if (isDigit(expression.charAt(i))) {
                number += expression.charAt(i);
            } else {
                addNumberValue(number, tokens);
                number = "";
                ExpressionError processError = processOperationCharacter(expression.charAt(i), operationStack, tokens);
                if (processError != null)
                    return new Tuple2<>(null, processError);
            }
        }

        // if there a number after all, add it to the tokens collection
        addNumberValue(number, tokens);

        // also push all remaining operations from the stack
        for(int j = operationStack.size() - 1; j >= 0; --j) {
            ExpressionError processError = addOperationToken(operationStack, tokens);
            if (processError != null)
                return new Tuple2<>(null, processError);
        }

        return new Tuple2<>(tokens, null);
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

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    private static boolean isOpenBracket(char c) {
        return c == '(';
    }
    private static boolean isCloseBracket(char c) {
        return c == ')';
    }

    void addNumberValue(String number, Vector<ExpressionToken> tokens) {
        if (number.length() > 0) {
            tokens.add(new ExpressionValue(number));
        }
    }

    ExpressionError addOperationToken(Vector<Character> opsStack, Vector<ExpressionToken> tokens) {
        if (opsStack.isEmpty())
            return new ExpressionError("unexpected condition met while processing operation token");

        String op = "";
        op += opsStack.elementAt(opsStack.size() - 1);
        tokens.add(new ExpressionOperation(op));
        opsStack.removeElementAt(opsStack.size() - 1);

        return null;
    }

    ExpressionError processOperationCharacter(Character operationChar, Vector<Character> operationStack, Vector<ExpressionToken> tokens) {
        if (isOpenBracket(operationChar)) {
            operationStack.add(operationChar);
        } else if (isCloseBracket(operationChar)) {
            ExpressionError opsError = processOperationsOnCloseBracket(tokens, operationStack);
            if (opsError != null)
                return opsError;
        } else {
            if (!ExpressionOperation.isOperation(operationChar))
                return new ExpressionError("input string had incorrect format : invalid operation '" + operationChar + "'");
            int thisOperationPriority = getOperationPriority(operationChar);
            for(int j = operationStack.size() - 1; j >= 0 && !isOpenBracket(operationStack.elementAt(j)) ; --j) {
                if (thisOperationPriority < getOperationPriority(operationStack.elementAt(j)))
                    break;
                ExpressionError processError = addOperationToken(operationStack, tokens);
                if(processError != null)
                    return processError;
            }
            operationStack.add(operationChar);
        }
        return null;
    }

    ExpressionError processOperationsOnCloseBracket(Vector<ExpressionToken> tokens, Vector<Character> operationStack) {
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
        if (!correct)
            return new ExpressionError("input string had incorrect format");
        return null;
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
