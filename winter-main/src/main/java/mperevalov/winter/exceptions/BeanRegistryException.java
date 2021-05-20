package mperevalov.winter.exceptions;

public class BeanRegistryException extends RuntimeException {
    public BeanRegistryException(String message) {
        super(message);
    }

    public BeanRegistryException(Throwable throwable) {
        super(throwable);
    }
}
