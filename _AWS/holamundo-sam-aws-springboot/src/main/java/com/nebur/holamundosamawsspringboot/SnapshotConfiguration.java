package com.nebur.holamundosamawsspringboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.context.annotation.Configuration;

/**
 * https://aws.amazon.com/es/about-aws/whats-new/2022/11/aws-lambda-snapstart-java-functions/
 * 
 * clase opcional, para usar spanshot, y hacer operaciones antes de guardar el snapshot
 * y antes de cargarlo
 * 
 * Lambda SnapStart está disponible de manera general en las siguientes regiones de AWS: Este de EE. UU. (Ohio), Este de EE. UU. (Norte de Virginia), Oeste de EE. UU. (Oregón), Asia-Pacífico (Singapur), Asia-Pacífico (Tokio), Asia-Pacífico (Sídney), Europa (Fráncfort), Europa (Irlanda) y Europa (Estocolmo). 
 * 
 * @author ruben.aguado
 *
 */
@Configuration
public class SnapshotConfiguration implements Resource {
    private static final Logger LOG = LogManager.getLogger();

    public SnapshotConfiguration()
    {
        LOG.info("Snapshot config constructor");
        Core.getGlobalContext().register(SnapshotConfiguration.this);

    }

    @Override
    public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
        LOG.info("Before checkpoint");
        //close database conection, etc 
    }

    @Override
    public void afterRestore(Context<? extends Resource> context) throws Exception {
        LOG.info("Restoring");
    }
}