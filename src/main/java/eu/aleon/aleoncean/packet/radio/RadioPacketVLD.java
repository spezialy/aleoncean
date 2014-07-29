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
package eu.aleon.aleoncean.packet.radio;

import eu.aleon.aleoncean.packet.RadioChoice;
import eu.aleon.aleoncean.packet.RadioPacketVariableLength;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataVLD;
import java.util.Arrays;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RadioPacketVLD extends RadioPacketVariableLength {

    public RadioPacketVLD() {
        super(UserDataVLD.DATA_LENGTH_MIN, UserDataVLD.DATA_LENGTH_MAX, RadioChoice.RORG_VLD);
    }

    @Override
    public String toString() {
        final String str = String.format(
                "RadioPacketVLD{%s, data=%s}",
                super.toString(), Arrays.toString(getUserDataRaw())
        );
        return str;
    }

}
