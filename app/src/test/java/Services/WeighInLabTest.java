package Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOError;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.IOException;

import static org.junit.Assert.*;

/**
 * Created by fst on 2016-02-24.
 */
public class WeighInLabTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testRealm() throws IOException, java.io.IOException {
        File tempFolder = testFolder.newFolder("realmData");
        RealmConfiguration config = new RealmConfiguration.Builder(tempFolder).build();

        Realm realm = Realm.getInstance(config);

        realm.close();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
}