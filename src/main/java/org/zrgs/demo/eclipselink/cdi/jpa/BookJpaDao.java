package org.zrgs.demo.eclipselink.cdi.jpa;

import info.cukes.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class BookJpaDao extends GenericJpaDao<Book, Long> {
}
