package geek.arithmetic;

import java.io.*;
import java.util.StringTokenizer;

class InputReader {
    private String filePath;
    private BufferedReader reader;
    private StringTokenizer tokenizer;
    public enum InputErrorType {NoError, IncorrectFormat, IOError, EOF}

    InputReader(String inputPath) throws FileNotFoundException {
        filePath = inputPath;
        FileReader fr = new FileReader(inputPath);
        reader = new BufferedReader(fr);
    }
    private String getNextToken() throws IOException {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }
    public int nextInt() throws IOException {
        return Integer.valueOf(getNextToken());
    }
    public String nextString() throws IOException {
        return getNextToken();
    }
    public Tuple2<String, InputErrorType> getLine() throws IOException {
        if (tokenizer != null && tokenizer.hasMoreTokens()) {
            return new Tuple2<>("", InputErrorType.IncorrectFormat);
        }
        // NOTE: we handle EOF and format errors here and transform it to return code value
        // other IOExceptions are allowed to go out of this func
        String result;
        try {
            result = reader.readLine();
        } catch (EOFException eof) {
            return new Tuple2<>("", InputErrorType.EOF);
        }
        return new Tuple2<>(result, InputErrorType.NoError);
    }
}