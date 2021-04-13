package geek.arithmetic;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExpressionContext {

    private InputReader reader;

    public ExpressionContext(String inputPath) throws FileNotFoundException {
        reader = new InputReader(inputPath);
    }
    public void Run() throws IOException {
        Tuple2<String, InputReader.InputErrorType> readResult = reader.getLine();
        while(readResult.getError() ==  InputReader.InputErrorType.NoError) {
            // NOTE: here we just assume/hardcode integer expression type here
            // later solution could be extended to other types, when more precision or else is needed
            EvaluatorsFactory.ExpressionPrecision type = EvaluatorsFactory.ExpressionPrecision.Integer;

            // get evaluator and calculate expression value
            ExpressionEvaluator evaluator = EvaluatorsFactory.getEvaluator(type);
            Tuple2<Double, ExpressionError> expressionResult = evaluator.EvaluateExpression(readResult.getValue());

            if (expressionResult.getError() != null)
                System.out.println("Expression " + readResult.getValue() + " was not evaluated due to error : " + expressionResult.getError().toString());
            else
                System.out.println("Expression " + readResult.getValue() + " value is : " + String.valueOf(expressionResult.getValue()));

            readResult = reader.getLine();
        }
    }
}
