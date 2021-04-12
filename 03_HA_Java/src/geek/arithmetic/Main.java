package geek.arithmetic;

import java.io.FileNotFoundException;

public class Main {

    private static final String in_file_name = "inputs//input.txt";

    public static void main(String[] args) {
        try {
            ExpressionContext appContext = new ExpressionContext(in_file_name);
            appContext.Run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
