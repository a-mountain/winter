package mperevalov.winter.core;

import mperevalov.winter.exceptions.BeanConstructionException;

import java.lang.reflect.Parameter;

public class BeanParameterValue {

    private final Parameter parameter;
    private Object parameterValue;

    public BeanParameterValue(Parameter parameter) {
        this.parameter = parameter;
    }

    public Object parameterValue() {
        return parameterValue;
    }

    public boolean isEmpty() {
        return parameterValue == null;
    }

    public void setParameterValue(Object value) {
        if (this.parameterValue != null) {
            throw new BeanConstructionException("Parameter is already initialized");
        }
        this.parameterValue = value;
    }

    public Parameter parameter() {
        return parameter;
    }
}
