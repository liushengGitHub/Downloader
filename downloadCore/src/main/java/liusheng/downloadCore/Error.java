package liusheng.downloadCore;

public class Error {
    long sum;
    Exception e;

    public long getSum() {
        return sum;
    }

    public Exception getE() {
        return e;
    }

    public Error(long sum, Exception e) {
        this.sum = sum;
        this.e = e;
    }
}