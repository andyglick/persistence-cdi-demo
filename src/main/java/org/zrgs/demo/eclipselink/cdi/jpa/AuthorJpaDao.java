package org.zrgs.demo.eclipselink.cdi.jpa;

import info.cukes.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AuthorJpaDao extends GenericJpaDao<Author, Long> {
}
