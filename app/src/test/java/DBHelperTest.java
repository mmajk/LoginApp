import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.example.jakob.loginapp.dal.DBHelper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import junit.framework.TestCase;


public class DBHelperTest extends TestCase {

    DBHelper myDBHelper;
    Context context;

    @Before
    public void setUp(){

        //myDBHelper = new DBHelper(this);
    }

    public void tearDDown(){

    }


    @Test
    public void testValidation(){
        assertTrue(2 == 2);
    }

    public void findRegistredUserTest(){

        //assertNotNull(myDBHelper);
    }
}
