package com.humanresources.assistant.backend.tools.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUpdate<T> {

    @Autowired
    ApplicationContext applicationContext;

    public void updateBeanWithSpecificValue(String beanName, T newWebClient) {
        DefaultSingletonBeanRegistry registry =
            ((DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory());
        registry.destroySingleton(beanName);
        registry.registerSingleton(beanName, newWebClient);
    }
}
