package geek.arithmetic;

public class ExpressionOperation implements ExpressionToken {
    public static boolean isOperation(Character chr) {
        return chr  == '+' || chr  == '-' || chr == '*' || chr == '/';
    }
    public Tuple2<Integer, ExpressionError> applyOperation(int lhs, int rhs) {
        if (operationCode.length() != 1 || !isOperation(operationCode.charAt(0)))
            return new Tuple2(null, "Invalid operation :" + operationCode);
        long result = 0;
        // apply operation
        if (operationCode.equals("+")) {
            result = (long)(lhs) + (long)(rhs);
        } else if (operationCode.equals("-")) {
            result = (long)(lhs) - (long)(rhs);
        } else if (operationCode.equals("*")) {
            result = (long)(lhs) * (long)(rhs);
        } else if (operationCode.equals("/")) {
            if (rhs == 0)
                return new Tuple2(null, "DivZero");
            result = (long)(lhs) / (long)(rhs);
        } else {
            return new Tuple2(null, "UnrecognizedOperation");
        }
        // check overflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return new Tuple2(null, "Overflow");
        }
        // return result
        return new Tuple2((int)result, null);
    }

    public ExpressionOperation(String token) {
        operationCode = token;
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.Operation;
    }
    @Override
    public String toString() {
        return operationCode;
    }

    private String operationCode;
}
