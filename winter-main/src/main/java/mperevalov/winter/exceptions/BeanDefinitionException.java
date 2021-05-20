package mperevalov.winter.exceptions;

public class BeanDefinitionException extends RuntimeException {
    public BeanDefinitionException(String message) {
        super(message);
    }

    public BeanDefinitionException(Throwable throwable) {
        super(throwable);
    }
}