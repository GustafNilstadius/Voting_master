package model;

import com.mongodb.connection.Stream;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.zip.CRC32;

/**
 * @deprecated
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */
public class Generate_Keys extends AbstractVerticle {
    private static String COLLECTION = "keys";
    @Override
    public void start() throws Exception {
        super.start();

        String key_raw = new BigInteger(config().getInteger("key_len"), new SecureRandom()).toString(32);
        CRC32 crc = new CRC32();
        crc.update(key_raw.getBytes());
        key_raw += "" + crc.getValue();
        //TODO genereate checksum
        //TODO named mongo pool
        MongoClient mongo = MongoClient.createShared(vertx, config());
        mongo.insert(COLLECTION, new JsonObject().put("_id", key_raw).put("vote", 1), result -> {
            if (result.succeeded()){
                //nop
            } else {

            }
        });


    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
