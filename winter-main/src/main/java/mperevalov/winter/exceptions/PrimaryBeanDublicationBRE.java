package mperevalov.winter.exceptions;

import mperevalov.winter.core.BeanDefinition;

public class PrimaryBeanDublicationBRE extends BeanRegistryException {

    public PrimaryBeanDublicationBRE(BeanDefinition<?> definition1, BeanDefinition<?> definition2) {
        super("There is more than one primary annotated beans: %s"
                .formatted("%s, %s".formatted(definition1.name(), definition2.name())));
    }

    public PrimaryBeanDublicationBRE(Throwable throwable) {
        super(throwable);
    }
}
