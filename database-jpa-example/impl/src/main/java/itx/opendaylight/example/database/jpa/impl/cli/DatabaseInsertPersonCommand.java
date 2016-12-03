/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.jpa.impl.cli;

import itx.opendaylight.example.database.jpa.impl.PersonDataService;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 28.11.2016.
 */
@Command(scope = "jpa-example", name = "insert", description = "insert person into database")
public class DatabaseInsertPersonCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseInsertPersonCommand.class);

    private PersonDataService personService;

    @Argument(index = 0, name = "firstName",
            description = "persons's first name",
            required = true,
            multiValued = false)
    private String firstName;

    @Argument(index = 1, name = "secondName",
            description = "person's second name",
            required = true,
            multiValued = false)
    private String secondName;

    public DatabaseInsertPersonCommand(PersonDataService personService) {
        LOG.info("DatabaseInsertPersonCommand: " + (personService != null));
        this.personService = personService;
    }

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("doExecute");
        try {
            personService.createPerson(firstName, secondName);
            return "OK ";
        } catch (Exception e) {
            return "FAILED";
        }
    }

}
