package model;

import de.flapdoodle.embed.mongo.MongodProcess;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.TestContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-17.
 */
public class Generate_Keys_SupervicorTEST {
    private Vertx vertx;
    private static MongodProcess MONGO;
    private MongoClient client;
    private static int MONGO_PORT = 27017;
    private static JsonObject db_connect;

    @BeforeClass
    public static void initialize() throws IOException {
        db_connect = new JsonObject()
                .put("http.port", 8080)
                .put("db_name", "keys")
                .put("connection_string", "mongodb://localhost:" + MONGO_PORT)
                .put("key_len", 1024);

        /*MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MONGO = mongodExecutable.start();*/
    }

    @AfterClass
    public static void shutdown(){
        //MONGO.stop();
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }


}
