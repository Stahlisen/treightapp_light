package Services;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOError;
import java.util.Date;
import java.util.List;

import Model.RealmWeighIn;
import Model.WeighIn;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.IOException;

import static org.junit.Assert.*;

/**
 * Created by fst on 2016-02-24.
 */
public class WeighInLabTest extends InstrumentationTestCase{

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testRealm() throws IOException, java.io.IOException {
        File tempFolder = testFolder.newFolder("realmData");
        RealmConfiguration config = new RealmConfiguration.Builder(tempFolder).build();

        Realm realm = Realm.getInstance(config);

        WeighIn weighin = new WeighIn();
        weighin.setDate(new Date());
        weighin.setWeight(82);

        realm.beginTransaction();

        RealmWeighIn rw = realm.createObject(RealmWeighIn.class);
        rw.setId(1);
        rw.setPicturefilepath("");
        rw.setWeight(weighin.getWeight());
        rw.setDate(weighin.getDate());

        realm.commitTransaction();

        List<WeighIn> weighins = WeighInLab.get(getInstrumentation().getContext()).getAllWeighIns(getInstrumentation().getContext());
        Assert.assertNotNull(weighins);
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
}