/**
 * Copyright © 2016-2020 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.transport.lwm2m.bootstrap;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.thingsboard.server.transport.lwm2m.server.LwM2MTransportContextServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service("LwM2MTransportBootstrapServerInitializer")
@ConditionalOnExpression("'${service.type:null}'=='tb-transport' || ('${service.type:null}'=='monolith' && '${transport.lwm2m.enabled}'=='true' && '${transport.lwm2m.bootstrap.enable}'=='true')")
public class LwM2MTransportBootstrapServerInitializer {

    @Autowired
    private LeshanBootstrapServer lhBServer;

    @Autowired
    private LwM2MTransportContextServer context;

    @PostConstruct
    public void init() {
//        this.context.setSessions(new ConcurrentHashMap<>());
        this.lhBServer.start();
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("Stopping LwM2M transport Bootstrap Server!");
        try {
            lhBServer.destroy();
        } finally {
        }
        log.info("LwM2M transport Bootstrap Server stopped!");
    }

}
