/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl.cli;

import itx.opendaylight.example.database.impl.PersonDataService;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 28.11.2016.
 */
@Command(scope = "jdbc-example", name = "delete", description = "delete person from database")
public class DatabaseDeletePersonCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseDeletePersonCommand.class);

    private PersonDataService personService;

    @Argument(index = 0, name = "id",
            description = "persons's database id",
            required = true,
            multiValued = false)
    private Long id;

    public DatabaseDeletePersonCommand(PersonDataService personService) {
        LOG.info("DatabaseInsertPersonCommand: " + (personService != null));
        this.personService = personService;
    }

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("doExecute");
        try {
            personService.deletePerson(id);
            return "OK ";
        } catch (Exception e) {
            return "FAILED";
        }
    }

}
