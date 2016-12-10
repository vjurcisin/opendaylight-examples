/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.cli;

import itx.opendaylight.examples.cluster.demo.impl.members.ClusterMemberManager;
import itx.opendaylight.examples.cluster.demo.impl.members.MemberInfo;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by juraj on 9.12.2016.
 */
@Command(scope = "cluster", name = "showMembers", description = "show cluster members")
public class ShowMembersCommand  extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ShowMembersCommand.class);

    private ClusterMemberManager clusterMemberManager;

    @Override
    protected Object doExecute() throws Exception {
        List<MemberInfo> members = clusterMemberManager.getMembers();
        StringBuffer sb = new StringBuffer();
        members.forEach( m -> {
            sb.append(m.toString());
            sb.append("\n");
        });
        sb.append("\nOK\n");
        return sb.toString();
    }

    public void setClusterMemberManager(ClusterMemberManager clusterMemberManager) {
        this.clusterMemberManager = clusterMemberManager;
    }

}
