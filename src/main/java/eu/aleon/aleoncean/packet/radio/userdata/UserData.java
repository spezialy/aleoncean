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

import eu.aleon.aleoncean.packet.RadioPacket;
import eu.aleon.aleoncean.util.Bits;
import eu.aleon.aleoncean.util.CalculationUtil;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public abstract class UserData {

    private byte[] userData;

    public UserData(int size) {
        userData = new byte[size];
    }

    public UserData(byte[] data) {
        this.userData = data;
    }

    public byte[] getUserData() {
        return userData;
    }

    public void setUserData(byte[] userData) {
        this.userData = userData;
    }

    protected int convPosDbToReal(int dbPos) {
        return convPosDbToReal(userData, dbPos);
    }

    public static int convPosDbToReal(final byte[] userData, int dbPos) {
        return userData.length - 1 - dbPos;
    }

    protected byte getDb(int dbPos) {
        return getDb(userData, dbPos);
    }

    public static byte getDb(final byte[] userData, int dbPos) {
        return userData[convPosDbToReal(userData, dbPos)];
    }

    protected void setDb(int dbPos, byte value) {
        setDb(userData, dbPos, value);
    }

    public static void setDb(final byte[] userData, int dbPos, byte value) {
        userData[convPosDbToReal(userData, dbPos)] = value;
    }

    protected int getDataBit(int db, int bit) {
        return getDataBit(userData, db, bit);
    }

    public static int getDataBit(final byte[] userData, int db, int bit) {
        return Bits.getBit(getDb(userData, db), bit);
    }

    protected void setDataBit(int db, int bit, boolean value) {
        final int pos = convPosDbToReal(db);
        userData[pos] = Bits.setBit(userData[pos], bit, value);
    }

    protected void setDataBit(int db, int bit, int value) {
        final int pos = convPosDbToReal(db);
        userData[pos] = Bits.setBit(userData[pos], bit, value);
    }

    protected long getDataRange(int startDB, int startBit, int endDB, int endBit) {
        return getDataRange(userData, startDB, startBit, endDB, endBit);
    }

    public static long getDataRange(final byte[] userData, int startDB, int startBit, int endDB, int endBit) {
        assert startDB >= endDB || (startDB == endDB && startBit >= endBit);
        assert startDB <= userData.length - 1;

        final int realStartByte = convPosDbToReal(userData, startDB);
        final int realEndByte = convPosDbToReal(userData, endDB);

        return Bits.getBitsFromBytes(userData, realStartByte, startBit, realEndByte, endBit);
    }

    protected void setDataRange(long value, int startDB, int startBit, int endDB, int endBit) {
        // e.g. db3.5 ... db2.7
        assert startDB >= endDB || (startDB == endDB && startBit >= endBit);
        assert startDB <= userData.length - 1;

        final int realStartByte = convPosDbToReal(startDB);
        final int realEndByte = convPosDbToReal(endDB);

        Bits.setBitsOfBytes(value, userData, realStartByte, startBit, realEndByte, endBit);
    }

    /**
     * Extract a value of a given bit range and convert it to a scaled one.
     *
     * @param startDB  The data byte (EEP order) the value extraction should be start.
     * @param startBit The bit of the start byte the value extraction should be start.
     * @param endDB    The data byte (EEP order) the value extraction should be end.
     * @param endBit   The bit of the end byte the value extraction should be end.
     * @param rangeMin The lower limit of the range.
     * @param rangeMax The upper limit of the range.
     * @param scaleMin The lower limit of the scaled value.
     * @param scaleMax The upper limit of the scaled value.
     * @return Return a scaled value that does fit in given range.
     * @throws UserDataScaleValueException This exception is raised if the value extracted from given bit range does
     *                                     not fit in range.
     */
    protected double getScaleValue(int startDB, int startBit, int endDB, int endBit,
                                   long rangeMin, long rangeMax,
                                   double scaleMin, double scaleMax)
            throws UserDataScaleValueException {
        final long raw = getDataRange(startDB, startBit, endDB, endBit);
        return getScaleValue(raw, rangeMin, rangeMax, scaleMin, scaleMax);
    }

    /**
     * Convert a raw value to a scaled one with respect ranges.
     *
     * @param raw      The value that should be scaled.
     * @param rangeMin The lower limit of the range.
     * @param rangeMax The upper limit of the range.
     * @param scaleMin The lower limit of the scaled value.
     * @param scaleMax The upper limit of the scaled value.
     * @return Return a scaled value that does fit in given range.
     * @throws UserDataScaleValueException This exception is raised if the value extracted from given bit range does
     *                                     not fit in range.
     */
    protected double getScaleValue(long raw,
                                   long rangeMin, long rangeMax,
                                   double scaleMin, double scaleMax)
            throws UserDataScaleValueException {

        /*
         * The range could also be inverse (255..0 instead of 0..255), so we have to improve the check.
         */
        if (raw < rangeMin && raw < rangeMax
            || raw > rangeMin && raw > rangeMax) {
            throw new UserDataScaleValueException(String.format("The coded value does not fit in range (min: %d, max: %d, value: %d).", rangeMin, rangeMax, raw));
        }

        final double scale = CalculationUtil.rangeToScale(raw, rangeMin, rangeMax, scaleMin, scaleMax);
        return scale;
    }

    protected long getRangeValue(double scale,
                                 double scaleMin, double scaleMax,
                                 long rangeMin, long rangeMax)
            throws UserDataScaleValueException {

        /*
         * The range could also be inverse (255..0 instead of 0..255), so we have to improve the check.
         */
        if (scale < scaleMin && scale < scaleMax
            || scale > scaleMin && scale > scaleMax) {
            throw new UserDataScaleValueException(String.format(
                    "The scale value does not fit in range (min: %f, max: %f, value: %f).",
                    scaleMin, scaleMax, scale));
        }

        final long raw = CalculationUtil.scaleToRange(scale, scaleMin, scaleMax, rangeMin, rangeMax);
        return raw;
    }

    public abstract RadioPacket generateRadioPacket();
}
