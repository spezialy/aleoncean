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
package eu.aleon.aleoncean.packet.radio.userdata;

import eu.aleon.aleoncean.values.LearnType4BS;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
/* TODO: Variant 3 is missing.
 * Could we know if a packet is variant 3??
 */
public class UserData4BSTeachIn extends UserData4BS {

    public static final int FUNC_MIN = 0x00;
    public static final int FUNC_MAX = 0x3F;
    public static final int TYPE_MIN = 0x00;
    public static final int TYPE_MAX = 0x7F;
    public static final int MANUFACTURER_ID_MIN = 0x0000;
    public static final int MANUFACTURER_ID_MAX = 0x07FF;

    public UserData4BSTeachIn() {
    }

    public UserData4BSTeachIn(final byte[] data) {
        super(data);
    }

    public byte getFunc() {
        return (byte) getDataRange(3, 7, 3, 2);
    }

    public void setFunc(final byte func) {
        setDataRange(func, 3, 7, 3, 2);
    }

    public byte getType() {
        return (byte) getDataRange(3, 1, 2, 3);
    }

    public void setType(final byte type) {
        setDataRange(type, 3, 1, 2, 3);
    }

    public short getManufacturerId() {
        return (short) getDataRange(2, 2, 1, 0);
    }

    public void setManufacturerId(short manufacturerId) {
        setDataRange(manufacturerId, 2, 2, 1, 0);
    }

    public LearnType4BS getLearnType4BS() {
        if (getDataBit(0, 7) == 0) {
            return LearnType4BS.WITHOUT_EEP_NUM_WITHOUT_MANU_ID;
        } else {
            return LearnType4BS.WITH_EEP_NUM_WITH_MANU_ID;
        }
    }

}
