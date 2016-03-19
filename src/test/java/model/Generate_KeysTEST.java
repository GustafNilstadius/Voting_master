//package model;
//
//
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import io.vertx.core.DeploymentOptions;
//import io.vertx.core.*;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.mongo.MongoClient;
//import io.vertx.ext.unit.*;
//import io.vertx.ext.unit.junit.Repeat;
//import io.vertx.ext.unit.junit.VertxUnitRunner;
//import io.vertx.ext.unit.report.ReportOptions;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
//import model.MongoDBSetup;
//import org.junit.*;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//
///**
// * @author Gustaf Nilstadius
// *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
// */
//
//@RunWith(VertxUnitRunner.class)
//public class Generate_KeysTEST {
//    private Vertx vertx;
//    private static MongodProcess MONGO;
//    private MongoClient client;
//    private static int MONGO_PORT = 12345;
//
//    @BeforeClass
//    public static void initialize() throws IOException {
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//
//        IMongodConfig mongodConfig = new MongodConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
//                .build();
//
//        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
//        MONGO = mongodExecutable.start();
//    }
//
//    @AfterClass
//    public static void shutdown(){
//        MONGO.stop();
//    }
//
//    @After
//    public void tearDown(TestContext context) {
//        vertx.close(context.asyncAssertSuccess());
//    }
//
//    @Before
//    public void setUp(TestContext context){
//        vertx = Vertx.vertx();
//
//        DeploymentOptions options = new DeploymentOptions()
//                .setConfig(new JsonObject()
//                        .put("db_name", "whiskies-test")
//                        .put("connection_string", "mongodb://localhost:" + MONGO_PORT)
//                );
//        //DeploymentOptions options = new DeploymentOptions().setWorker(true);
//        vertx.deployVerticle(Generate_Keys.class.getName(), options, context.asyncAssertSuccess());
//        client = MongoClient.createShared(vertx, new JsonObject().put("connection_string", "mongodb://localhost:" + MONGO_PORT));
//        /*client.remove("keys", new JsonObject(), res -> {
//            context.assertTrue(res.succeeded());
//        });*/
//        System.out.println("Hello nooobz");
//    }
//
//
//    @Test
//    public void generate_One_Key(TestContext context){
//        final Async async = context.async();
//        client.count("keys", new JsonObject(), res -> {
//            if(res.succeeded()){
//                if(res.result() == 1){
//                    context.assertTrue(res.result() == 1);
//                    async.complete();
//                } else {
//                    context.assertTrue(false);
//                    async.complete();
//                }
//            } else{
//                context.assertFalse(res.succeeded());
//                async.complete();
//            }
//        });
//    }
//
//}
