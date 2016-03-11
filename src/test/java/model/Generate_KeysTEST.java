package model;


import de.flapdoodle.embed.mongo.config.IMongodConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.*;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.unit.report.ReportOptions;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import model.MongoDBSetup;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */

@RunWith(VertxUnitRunner.class)
public class Generate_KeysTEST {
    private Vertx vertx;
    private static MongodProcess MONGO;

    @BeforeClass
    public static void initialize() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(12345, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MONGO = mongodExecutable.start();
    }

    @AfterClass
    public static void shutdown(){
        MONGO.stop();
    }

    @Before
    public void setUp(TestContext context){
        vertx = Vertx.vertx();

        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        vertx.deployVerticle(Generate_Keys.class.getName(), options, context.asyncAssertSuccess());
    }


    @Test
    public void generate_One_Key(TestContext context){
        final Async async = context.async();
        MongoClient client = MongoClient.createShared(vertx, new JsonObject().put("connection_string", "mongodb://localhost:12345"));
        client.count("keys", new JsonObject(), res -> {
            if(res.succeeded()){
                context.assertEquals(res.result(), 1);
                async.complete();
            }
        });
    }

}
