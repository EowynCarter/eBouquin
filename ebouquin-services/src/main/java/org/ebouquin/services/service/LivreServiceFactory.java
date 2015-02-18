package org.ebouquin.services.service;

import org.ebouquin.ebouquinFormatPlugin.LivreFichierService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by catherine on 23/12/14.
 */


@Service
public class LivreServiceFactory implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    Map<String, LivreFichierService> services = new HashMap<String, LivreFichierService>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        String[] beansNames = applicationContext.getBeanNamesForType(LivreFichierService.class);

        for (String beansName : beansNames) {
            LivreFichierService bean = (LivreFichierService) applicationContext.getBean(beansName);

            for (String ext : bean.extention()) {
                services.put(ext, bean);
            }
        }
    }

    public LivreFichierService getService(String extention) {

        return services.get(extention);
    }
}
