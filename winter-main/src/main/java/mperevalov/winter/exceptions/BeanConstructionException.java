package mperevalov.winter.exceptions;

public class BeanConstructionException extends RuntimeException {
    public BeanConstructionException(String message) {
        super(message);
    }

    public BeanConstructionException(Throwable throwable) {
        super(throwable);
    }
}
