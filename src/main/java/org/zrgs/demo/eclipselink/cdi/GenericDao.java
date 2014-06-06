package org.zrgs.demo.eclipselink.cdi;

import javax.persistence.EntityManager;
import java.io.Serializable;

public interface GenericDao<T, I extends Serializable> {
    T save(T t);

    void delete(T t);

    T find(I id);

    long count();

    T merge(T t);

    EntityManager getEntityManager();
}
