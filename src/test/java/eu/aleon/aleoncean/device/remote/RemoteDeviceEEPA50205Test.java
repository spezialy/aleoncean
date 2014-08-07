/**
 * 
 */
package eu.aleon.aleoncean.device.remote;

import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.packet.radio.RadioPacket4BS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataEEPA50205;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Thomas Stezaly (thomas.stezaly@aleuon.eu)
 *
 */
public class RemoteDeviceEEPA50205Test {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetGetTemperature() {

        final double temperatureSet = 20.0;
        final UserDataEEPA50205 userData = new UserDataEEPA50205();

        try {
            userData.setTemperature(temperatureSet);
            userData.setTeachIn(false);
        } catch (UserDataScaleValueException e) {
            fail("Throws exception: UserDataScaleValueException");
        }

        final RadioPacket4BS packet = userData.generateRadioPacket();
        final RemoteDeviceEEPA50205 testDevice = new RemoteDeviceEEPA50205(null, packet.getSenderId(), packet.getDestinationId());

        testDevice.parseRadioPacket(packet);

        double temperatureGet = testDevice.getTemperature();
        double delta = temperatureGet - temperatureSet;

        System.out.println("temperatureSet: "+temperatureSet);
        System.out.println("temperatureGet: "+temperatureGet);

        // We use a delta of 0.2, because one range point fits to 0.15 degree.
        // So is this value acceptable
        assertTrue(delta < 0.2);
    }
}
