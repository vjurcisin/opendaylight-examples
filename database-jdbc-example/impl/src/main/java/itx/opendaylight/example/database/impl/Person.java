/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.impl;

/**
 * Created by gergej on 28.11.2016.
 */
public class Person {

    private Long id;
    private String firstName;
    private String secondName;

    public Person() {
    }

    public Person(Long id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Person) {
            Person otherPerson = (Person)other;
            return this.id.equals(otherPerson.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return "PERSON: id=" + id + ", firstName=" + firstName + "; secondName=" + secondName;
    }

}
