/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.jpa.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by gergej on 3.12.2016.
 */
public class PersonDataServiceImpl implements PersonDataService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonDataServiceImpl.class);

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        LOG.info("setEntityManager: " + (entityManager != null));
        this.entityManager = entityManager;
    }

    public void init() {
        LOG.info("init: " + (entityManager != null));
    }

    public void close() {
        LOG.info("close.");
    }

    @Override
    public void createPerson(String firstName, String secondName) throws Exception {
        LOG.info("createUser: " + firstName);
        Person user = new Person(firstName, secondName);
        entityManager.persist(user);
    }

    @Override
    public List<Person> getPersons() {
        LOG.info("getUsers");
        return entityManager.createNamedQuery("getAllPersons", Person.class).getResultList();
    }

    @Override
    public void deletePerson(Long id) throws Exception {
        LOG.info("deleteUser: " + id);
        Person person = entityManager.find(Person.class,id);
        entityManager.remove(person);
    }

}
