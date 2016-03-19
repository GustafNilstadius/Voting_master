//package model;
//
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
//
///**
// * @author Gustaf Nilstadius
// *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
// */
//public class MongoDBSetup {
//    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
//    private static MongodExecutable _mongodExe;
//    private static MongodProcess _mongod;
//
//    public static void setUp () throws Exception{
//        _mongodExe = starter.prepare(new MongodConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net(12345, Network.localhostIsIPv6()))
//                .build());
//
//        _mongod = _mongodExe.start();
//    }
//
//    public static void tearDown() throws Exception {
//        _mongod.stop();
//        _mongodExe.stop();
//    }
//}
