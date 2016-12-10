/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

import java.util.List;

/**
 * Created by gergej on 10.12.2016.
 */
public interface ClusterStatus {

    public String getSelfAddress();

    public List<MemberInfo> getMembers();

    public Boolean isLeader();

}
