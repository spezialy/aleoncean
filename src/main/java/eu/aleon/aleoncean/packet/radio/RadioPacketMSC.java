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
import eu.aleon.aleoncean.packet.radio.userdata.UserDataMSC;
import java.util.Arrays;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RadioPacketMSC extends RadioPacketVariableLength {

    public RadioPacketMSC() {
        super(UserDataMSC.DATA_LENGTH_MIN, UserDataMSC.DATA_LENGTH_MAX, RadioChoice.RORG_MSC);
    }

    @Override
    public String toString() {
        final String str = String.format(
                "RadioPacketMSC{%s, data=%s}",
                super.toString(), Arrays.toString(getUserDataRaw())
        );
        return str;
    }

}
