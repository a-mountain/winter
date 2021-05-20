package mperevalov.winter.core;

import mperevalov.winter.extensions.interfaces.BeanBeforeConstructionEP;
import mperevalov.winter.extensions.interfaces.BeanPostConstructionEP;
import mperevalov.winter.extensions.interfaces.ProxyBeanPostConstructionEP;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BeanFactoryExtensionPoints {

    private final List<BeanBeforeConstructionEP> beanBeforeConstructionEPs = new CopyOnWriteArrayList<>();
    private final List<BeanPostConstructionEP> beanPostConstructionEPs = new CopyOnWriteArrayList<>();
    private final List<ProxyBeanPostConstructionEP> proxyBeanPostConstructionEPs = new CopyOnWriteArrayList<>();

    public void addBeanBeforeConstructionEp(BeanBeforeConstructionEP beanBeforeConstructionEP) {
        this.beanBeforeConstructionEPs.add(beanBeforeConstructionEP);
    }

    public void addProxyBeanPostConstructionEP(ProxyBeanPostConstructionEP proxyBeanPostConstructionEP) {
        this.proxyBeanPostConstructionEPs.add(proxyBeanPostConstructionEP);
    }

    public void addBeanPostConstructionEp(BeanPostConstructionEP beanPostConstructionEP) {
        this.beanPostConstructionEPs.add(beanPostConstructionEP);
    }

    public void beforeConstruction(Class<?> beanType, List<BeanParameterValue> parameters) {
        for (BeanBeforeConstructionEP beanBeforeConstructionEP : beanBeforeConstructionEPs) {
            beanBeforeConstructionEP.configure(beanType, parameters);
        }
    }

    public void postConstruction(Object bean) {
        for (BeanPostConstructionEP beanPostConstructionEP : beanPostConstructionEPs) {
            beanPostConstructionEP.configure(bean);
        }
    }

    public Object proxyPostConstruction(Object bean, Class<?> type) {
        Object proxy = bean;
        for (ProxyBeanPostConstructionEP proxyBeanPostConstructionEP : proxyBeanPostConstructionEPs) {
            proxy = proxyBeanPostConstructionEP.proxifyIfNeeded(proxy, type);
        }
        return proxy;
    }
}
