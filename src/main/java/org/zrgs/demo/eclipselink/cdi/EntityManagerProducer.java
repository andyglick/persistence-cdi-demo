package org.zrgs.demo.eclipselink.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;

public class EntityManagerProducer implements Serializable {

    private static final long serialVersionUID = -6934038219040491371L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("cucumber-spring-and-eclipselink");

    @Produces
    @Singleton
    public EntityManager createEntityManager() {
        try {
            return EntityManagerProducer.entityManagerFactory.createEntityManager();
        } catch(RuntimeException re) {

            LOGGER.error("Error while creating EntityManager from EntityManagerFactory.", re);

            throw re;
        }
    }

    public void closeEntityManager(@Disposes EntityManager entityManager)
    {
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }
}
