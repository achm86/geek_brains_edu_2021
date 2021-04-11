package geek.arithmetic;

public class Tuple2<Value, Error> {
    private Value value;
    private Error error;
    public Tuple2(Value val, Error err) {
        this.value = val;
        this.error = err;
    }
    public Value getValue() {
        return value;
    }
    public Error getError() {
        return error;
    }
}
