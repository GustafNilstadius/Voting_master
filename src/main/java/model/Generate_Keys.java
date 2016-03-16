package model;

import com.mongodb.connection.Stream;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */
public class Generate_Keys extends AbstractVerticle {
    private static String COLLECTION = "keys";
    @Override
    public void start() throws Exception {
        super.start();
        MongoClient mongo = MongoClient.createShared(vertx, config());
        mongo.insert(COLLECTION, new JsonObject().put("_id", new BigInteger(130, new SecureRandom()).toString(32)).put("vote", 1), result -> {
            if (result.succeeded()){
                System.out.println("Donne");
            }
        });


    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
