package mperevalov.winter.extensions;

import mperevalov.winter.core.BeanFactoryExtensionPoints;
import mperevalov.winter.extensions.interfaces.BeanBeforeConstructionEP;
import mperevalov.winter.extensions.interfaces.BeanPostConstructionEP;
import mperevalov.winter.extensions.interfaces.ExtensionPoint;
import mperevalov.winter.extensions.interfaces.ProxyBeanPostConstructionEP;

public class BeanFactoryExtensionPointsCollector implements BeanPostConstructionEP {

    private final BeanFactoryExtensionPoints extensionPoints;

    public BeanFactoryExtensionPointsCollector(BeanFactoryExtensionPoints extensionPoints) {
        this.extensionPoints = extensionPoints;
    }

    @Override
    public void configure(Object bean) {
        if (bean instanceof ExtensionPoint) {
            if (bean instanceof BeanBeforeConstructionEP b) {
                extensionPoints.addBeanBeforeConstructionEp(b);
            } else if (bean instanceof BeanPostConstructionEP b) {
                extensionPoints.addBeanPostConstructionEp(b);
            } else if (bean instanceof ProxyBeanPostConstructionEP b) {
                extensionPoints.addProxyBeanPostConstructionEP(b);
            }
        }
    }
}
