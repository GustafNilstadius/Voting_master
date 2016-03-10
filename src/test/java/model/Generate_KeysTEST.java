package model;


import io.vertx.ext.unit.TestSuite;
import model.MongoDBSetup;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2016-03-10.
 */
public class Generate_KeysTEST {


    public generate_One_Key(){
        TestSuite testSuite = TestSuite.create("Genereate_one_key");
        testSuite.before(context -> {
           setUp();
        });
        testSuite.test("generate_one_key", context -> {

        })

    }

}
