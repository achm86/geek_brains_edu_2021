package geek.arithmetic;

import java.io.FileNotFoundException;

public class Main {

    private static final String inFileName = "inputs//input.txt";

    public static void main(String[] args) {
        try {
            ExpressionContext appContext = new ExpressionContext(inFileName);
            appContext.Run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
