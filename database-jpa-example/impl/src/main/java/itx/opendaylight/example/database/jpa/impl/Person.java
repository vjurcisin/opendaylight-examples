/*
 * Copyright © 2016 Pantheon s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.database.jpa.impl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by juraj on 28.11.2016.
 */

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name="getAllPersons",
                query="SELECT p FROM Person p")
})
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", unique = true, nullable = false)
    private String firstName;

    @Column(name = "secondname", nullable = false)
    private String secondName;

    public Person() {
    }

    public Person(String firstName, String secondName) {
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
            Person otherUser = (Person) other;
            return this.id.equals(otherUser.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return "PERSON: id=" + id + ", firstName=" + firstName + ", secondName=" + secondName;
    }

}
