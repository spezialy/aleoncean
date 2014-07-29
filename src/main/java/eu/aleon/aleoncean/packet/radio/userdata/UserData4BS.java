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

import eu.aleon.aleoncean.packet.radio.RadioPacket4BS;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class UserData4BS extends UserData {

    public static final int DATA_LENGTH = 4;

    public UserData4BS() {
        super(DATA_LENGTH);
    }

    public UserData4BS(final byte[] data) {
        super(data);
        assert data.length == DATA_LENGTH;
    }

    public boolean isTeachIn() {
        return isTeachIn(getUserData());
    }

    public static boolean isTeachIn(final byte[] userData) {
        return getDataBit(userData, 0, 3) == 0;
    }

    @Override
    public RadioPacket4BS generateRadioPacket() {
        final RadioPacket4BS packet = new RadioPacket4BS();
        packet.setUserDataRaw(getUserData());
        return packet;
    }

}
