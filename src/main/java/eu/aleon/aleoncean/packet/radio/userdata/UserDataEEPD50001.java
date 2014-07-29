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
public class UserDataEEPD50001 extends UserData1BS {

    public UserDataEEPD50001(byte[] eepData) {
        super(eepData);
    }

    public boolean isContactClosed() {
        return getDataBit(0, 0) == 1;
    }
}
