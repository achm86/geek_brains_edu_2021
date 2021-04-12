package geek.arithmetic;

public class ExpressionOperation implements ExpressionToken {
    public static boolean isOperation(Character chr) {
        return chr  == '+' || chr  == '-' || chr == '*' || chr == '/';
    }
    public static Tuple2<Integer, ExpressionError> applyOperation(int lhs, int rhs, ExpressionOperation operation) {

        long result = 0;
        // apply operation
        if (operation.operationCode.equals("+")) {
            result = (long)(lhs) + (long)(rhs);
        } else if (operation.operationCode.equals("-")) {
            result = (long)(lhs) - (long)(rhs);
        } else if (operation.operationCode.equals("*")) {
            result = (long)(lhs) * (long)(rhs);
        } else if (operation.operationCode.equals("/")) {
            if (rhs == 0) {
                return new Tuple2(Integer.MAX_VALUE, "DivZero");
            }
            result = (long)(lhs) / (long)(rhs);
            return new Tuple2((int)result, null);
        } else {
            return new Tuple2(0, "UnrecognizedOperation");
        }

        // check overflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return new Tuple2(0, "Overflow");
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
