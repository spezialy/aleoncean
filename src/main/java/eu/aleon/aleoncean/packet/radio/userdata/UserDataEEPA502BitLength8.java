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

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class UserDataEEPA502BitLength8 extends UserDataEEPA502 {

    private static final long TEMPERATURE_RANGE_MIN = 255;
    private static final long TEMPERATURE_RANGE_MAX = 0;

    public UserDataEEPA502BitLength8(byte[] eepData,
                                     double tempScaleMin, double tempScaleMax) {
        super(eepData, 1, 7, 1, 0, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, tempScaleMin, tempScaleMax);
    }

}
