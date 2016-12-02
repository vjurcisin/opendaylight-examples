/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl.cli;

import itx.opendaylight.example.database.impl.Person;
import itx.opendaylight.example.database.impl.PersonDataService;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 28.11.2016.
 */
@Command(scope = "jdbc-example", name = "getall", description = "get all persond from database")
public class DatabaseGetPersonsCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseGetPersonsCommand.class);

    private PersonDataService personService;

    public DatabaseGetPersonsCommand(PersonDataService personService) {
        LOG.info("DatabaseGetPersonsCommand: " + (personService != null));
        this.personService = personService;
    }

    @Override
    protected Object doExecute() throws Exception {
        StringBuffer sb = new StringBuffer();
        LOG.info("doExecute");
        personService.getPersons().forEach(p -> {
            sb.append(p.toString());
            sb.append("\n");
        });
        sb.append("\nOK\n");
        return sb.toString();
    }

}
