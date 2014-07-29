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

import eu.aleon.aleoncean.values.Unit;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class UserDataEEPA502 extends UserData4BS {

    private final int tempStartDB;
    private final int tempStartBit;
    private final int tempEndDB;
    private final int tempEndBit;
    private final long tempRangeMin;
    private final long tempRangeMax;
    private final double tempScaleMin;
    private final double tempScaleMax;
    public static final Unit TEMPERATURE_UNIT = Unit.DEGREE_CELSIUS;

    public UserDataEEPA502(byte[] eepData,
                           int tempStartDB, int tempStartBit, int tempEndDB, int tempEndBit,
                           long tempRangeMin, long tempRangeMax,
                           double tempScaleMin, double tempScaleMax) {
        super(eepData);
        this.tempStartDB = tempStartDB;
        this.tempStartBit = tempStartBit;
        this.tempEndDB = tempEndDB;
        this.tempEndBit = tempEndBit;
        this.tempRangeMin = tempRangeMin;
        this.tempRangeMax = tempRangeMax;
        this.tempScaleMin = tempScaleMin;
        this.tempScaleMax = tempScaleMax;
    }

    public double getTemperature() throws UserDataScaleValueException {
        return getScaleValue(tempStartDB, tempStartBit, tempEndDB, tempEndBit,
                             tempRangeMin, tempRangeMax,
                             tempScaleMin, tempScaleMax);
    }
}
