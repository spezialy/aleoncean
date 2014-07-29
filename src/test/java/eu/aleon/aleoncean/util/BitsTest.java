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
package eu.aleon.aleoncean.util;

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
public class BitsTest {

    public BitsTest() {
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
     * Test of getBitsFromBytes method, of class Bits.
     */
    @Test
    public void testGetBitsFromBytes_offset_length() {
        System.out.println("getBitsFromBytes (offset, length)");
        final byte[] data = {(byte) 0xFF, (byte) 0xA5, (byte) 0x12};

        assertEquals((byte) 0xFF, (byte) Bits.getBitsFromBytes(data, 0, 8));
        assertEquals((byte) 0xA5, (byte) Bits.getBitsFromBytes(data, 8, 8));
        assertEquals((byte) 0x12, (byte) Bits.getBitsFromBytes(data, 16, 8));
        assertEquals((byte) 0x0F, (byte) Bits.getBitsFromBytes(data, 0, 4));
        assertEquals((byte) 0x0F, (byte) Bits.getBitsFromBytes(data, 4, 4));
        assertEquals((byte) 0x0A, (byte) Bits.getBitsFromBytes(data, 8, 4));
        assertEquals((byte) 0x05, (byte) Bits.getBitsFromBytes(data, 12, 4));
        assertEquals((byte) 0x01, (byte) Bits.getBitsFromBytes(data, 16, 4));
        assertEquals((byte) 0x02, (byte) Bits.getBitsFromBytes(data, 20, 4));
        assertEquals((byte) 0xFA, (byte) Bits.getBitsFromBytes(data, 4, 8));
        assertEquals((byte) 0x51, (byte) Bits.getBitsFromBytes(data, 12, 8));
    }

}
