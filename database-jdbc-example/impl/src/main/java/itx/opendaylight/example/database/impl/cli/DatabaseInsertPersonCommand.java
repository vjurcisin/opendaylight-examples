/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl.cli;

import itx.opendaylight.example.database.impl.PersonDataService;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.karaf.shell.console.AbstractAction;

/**
 * Created by gergej on 28.11.2016.
 */
@Command(name = "jdbc-example-insert", scope = "insert person into database", description = "insert person into database")
public class DatabaseInsertPersonCommand extends AbstractAction {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseInsertPersonCommand.class);

    private PersonDataService personService;

    @Option(name = "-n",
            aliases = { "--name" },
            description = "person's name",
            required = true,
            multiValued = false)
    private String name;

    public DatabaseInsertPersonCommand(PersonDataService personService) {
        LOG.info("DatabaseInsertPersonCommand: " + (personService != null));
        this.personService = personService;
    }

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("doExecute");
        try {
            personService.createPerson(name);
            return "OK " + name;
        } catch (Exception e) {
            return "FAILED";
        }
    }
}
