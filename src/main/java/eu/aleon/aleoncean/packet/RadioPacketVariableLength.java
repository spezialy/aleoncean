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

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RadioPacketVariableLength extends RadioPacket {

    private final int userDataLengthMin;

    private final int userDataLengthMax;

    public RadioPacketVariableLength(int userDataLengthMin, int userDataLengthMax, byte choice) {
        super(choice);
        this.userDataLengthMin = userDataLengthMin;
        this.userDataLengthMax = userDataLengthMax;
    }

    @Override
    public final void setUserDataRaw(byte[] userData) {
        assert userData.length >= userDataLengthMin;
        assert userData.length <= userDataLengthMax;
        super.setUserDataRaw(userData);
    }

    @Override
    public final byte[] getUserDataRaw() {
        return super.getUserDataRaw();
    }

}
