/*
 * Copyright (c) 2014 aleon GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Markus Rathgeb - initial API and implementation and/or initial documentation
 */
package eu.aleon.aleoncean.packet;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class EnOceanIdTest {

    public EnOceanIdTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fill method, of class EnOceanId.
     */
    @Test
    public void testFill_String() {
        System.out.println("fill");
        String id = "00:83:C0:F4";
        EnOceanId instance = new EnOceanId();
        instance.fill(id);
        assertEquals(instance.getLong(), 0x0083C0F4L);
    }

}
