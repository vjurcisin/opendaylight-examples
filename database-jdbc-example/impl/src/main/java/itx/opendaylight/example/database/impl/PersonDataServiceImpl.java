/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gergej on 28.11.2016.
 */
public class PersonDataServiceImpl implements PersonDataService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonDataServiceImpl.class);

    private DataSource dataSource;

    public PersonDataServiceImpl() {
        LOG.info("init constructor");
    }

    public void init() {
        LOG.info("init");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("create table person (name varchar(100))");
            LOG.info("table initialization OK");
        } catch (Exception e) {
            LOG.error("table initialization FAILED");
        } finally {
            closenoMatterWhat(connection);
        }
    }

    public void setDataSource(DataSource dataSource) {
        LOG.info("setDataSource: " + (dataSource != null));
        this.dataSource = dataSource;
    }

    @Override
    public void createPerson(String name) throws Exception {
        LOG.info("createPerson: " + name);
        Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("insert into person (name) values ('" + name + "')");
        closenoMatterWhat(connection);
        LOG.info("createPerson: " + name + " OK");
    }

    @Override
    public List<Person> getPersons() {
        LOG.info("getPersons");
        List<Person> persons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from person");
            while (rs.next()) {
                Person person = new Person(rs.getString(1));
                LOG.info("getPersons: " + person.getName());
                persons.add(person);
            }
            LOG.info("getPersons OK");
        } catch (Exception e) {
            LOG.error("getPersons FAILED");
        } finally {
            closenoMatterWhat(connection);
        }
        return persons;
    }

    private void closenoMatterWhat(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.info("DB connection close Error");
            }
        }
    }

}
