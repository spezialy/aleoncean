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
package eu.aleon.aleoncean.packet.commoncommand;

import eu.aleon.aleoncean.packet.CommonCommandCode;
import eu.aleon.aleoncean.packet.CommonCommandPacket;
import eu.aleon.aleoncean.packet.ResponsePacket;
import eu.aleon.aleoncean.packet.ResponseReturnCode;
import eu.aleon.aleoncean.packet.response.NoDataResponse;
import eu.aleon.aleoncean.packet.response.Response;
import eu.aleon.aleoncean.packet.response.UnknownResponseException;
import eu.aleon.aleoncean.packet.response.commoncommand.CoRdVersionResponseOk;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class CoRdVersionPacket extends CommonCommandPacket {

    public CoRdVersionPacket() {
        super(CommonCommandCode.CO_RD_VERSION);
    }

    @Override
    public Response inspectResponsePacket(ResponsePacket packet) throws UnknownResponseException {
        switch (packet.getReturnCode()) {
            case ResponseReturnCode.RET_OK:
                CoRdVersionResponseOk response = new CoRdVersionResponseOk();
                response.fromResponsePacket(packet);
                return response;

            case ResponseReturnCode.RET_NOT_SUPPORTED:
                return new NoDataResponse();

            default:
                throw new UnknownResponseException();
        }
    }
}
