package model;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.*;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestSuite;
import model.MongoDBSetup;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */

public class Generate_KeysTEST {
    private Vertx vertx = Vertx.vertx();


    public generate_One_Key(){
        TestSuite testSuite = TestSuite.create("Genereate_one_key");
        testSuite.before(context -> {
            try {
                MongoDBSetup.setUp();
            } catch (Exception e) {
                e.printStackTrace();
                context.fail("Mongo startup error");
            }
        });

        testSuite.test("generate_one_key", context -> {
            Async async = context.async();
            DeploymentOptions options = new DeploymentOptions().setWorker(true);
            vertx.deployVerticle("com.model.Generate_Keys", options);


        });

    }

}
