package model;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.*;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.zip.CRC32;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */

@RunWith(VertxUnitRunner.class)
public class Generate_KeysTEST {
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

    @Before
    public void setUp(TestContext context){
        vertx = Vertx.vertx();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(db_connect);

        client = MongoClient.createShared(vertx, db_connect);
        client.remove(db_connect.getString("db_name"), new JsonObject(), nop -> {});
        //DeploymentOptions options = new DeploymentOptions().setWorker(true);new JsonObject().put("connection_string", "mongodb://localhost:" + MONGO_PORT)
        vertx.deployVerticle(Generate_Keys.class.getName(), options, context.asyncAssertSuccess());

    }


    @Test
    public void generate_One_Key(TestContext context){
        final Async async = context.async();
        client.count("keys", new JsonObject(), res -> {
            if(res.succeeded()){
                if(res.result() == 1){
                    context.assertTrue(res.result() == 1);
                    async.complete();
                } else {
                    System.out.println("Count : " + res.result());
                    context.assertTrue(false);
                    async.complete();
                }
            } else{
                context.assertFalse(res.succeeded());
                async.complete();
            }
        });
    }

    @Test
    public void checksum_Key(TestContext context){
        final Async async = context.async();
        client.find("keys", new JsonObject(), res -> {
           if(res.succeeded()){
               if(res.result().size() == 1){
                   String key = res.result().get(0).getString("_id");
                   CRC32 sub_crc = new CRC32();
                   sub_crc.update(key.substring(0, key.length()-10).getBytes());
                   context.assertEquals("" + sub_crc.getValue(), key.substring(key.length()-10));
                   async.complete();
               }

           }
            async.complete();
        });

    }

}
