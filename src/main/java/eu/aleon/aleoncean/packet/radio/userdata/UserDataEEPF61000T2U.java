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

import eu.aleon.aleoncean.values.WindowHandlePosition;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class UserDataEEPF61000T2U extends UserDataEEPF610T2U {

    public UserDataEEPF61000T2U() {
    }

    public UserDataEEPF61000T2U(byte[] data) {
        super(data);
    }

    @Override
    public WindowHandlePosition getWindowHandlePosition() throws UserDataScaleValueException {
        return getWindowHandlePosition((byte) getDataRange(0, 7, 0, 4));
    }

    @Override
    public void setWindowHandlePosition(WindowHandlePosition windowHandlePosition) throws UserDataScaleValueException {
        setDataRange(getWindowHandlePositionByte(windowHandlePosition) & 0xFF, 0, 7, 0, 4);
    }

}
