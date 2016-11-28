/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl;

import java.util.List;

/**
 * Created by gergej on 28.11.2016.
 */
public interface PersonDataService {

    public void createPerson(String name) throws Exception;

    public List<Person> getPersons();

}
