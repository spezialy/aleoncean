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
package eu.aleon.aleoncean.device.remote;

import eu.aleon.aleoncean.device.DeviceParameter;
import eu.aleon.aleoncean.device.DeviceParameterUpdatedInitiation;
import eu.aleon.aleoncean.device.DeviceRPS;
import eu.aleon.aleoncean.device.RemoteDevice;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.radio.RadioPacketRPS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataEEPF61001Factory;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataEEPF61001T2U;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataRPS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.rxtx.ESP3Connector;
import eu.aleon.aleoncean.values.WindowHandlePosition;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RemoteDeviceEEPF61001 extends DeviceRPS implements RemoteDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDeviceEEPF61001.class);

    private WindowHandlePosition windowHandlePosition;

    public RemoteDeviceEEPF61001(ESP3Connector conn, EnOceanId addressRemote, EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

    public WindowHandlePosition getWindowHandlePosition() {
        return windowHandlePosition;
    }

    public void setWindowHandlePosition(final DeviceParameterUpdatedInitiation initiation, WindowHandlePosition windowHandlePosition) {
        final WindowHandlePosition oldWindowHandlePosition = this.windowHandlePosition;
        this.windowHandlePosition = windowHandlePosition;
        fireParameterChanged(DeviceParameter.WINDOW_HANDLE_POSITION, initiation, oldWindowHandlePosition, windowHandlePosition);
    }

    public void parseT2U(UserDataEEPF61001T2U userData) {
        try {
            setWindowHandlePosition(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getWindowHandlePosition());
        } catch (UserDataScaleValueException ex) {
            LOGGER.warn("Received not parsable window handle position.");
        }
    }

    @Override
    public void parseRadioPacketRPS(RadioPacketRPS packet) {
        UserDataRPS userData = UserDataEEPF61001Factory.getPacketData(packet);
        if (userData instanceof UserDataEEPF61001T2U) {
            parseT2U((UserDataEEPF61001T2U) userData);
        } else {
            LOGGER.debug("Cannot handle user data.");
        }
    }

    @Override
    protected void fillParameters(Set<DeviceParameter> params) {
        params.add(DeviceParameter.WINDOW_HANDLE_POSITION);
    }

    @Override
    public Object getByParameter(DeviceParameter parameter) throws IllegalArgumentException {
        switch (parameter) {
            case WINDOW_HANDLE_POSITION:
                return getWindowHandlePosition();
            default:
                return super.getByParameter(parameter);
        }
    }

    @Override
    public void setByParameter(DeviceParameter parameter, Object value) throws IllegalArgumentException {
        assert DeviceParameter.getSupportedClass(parameter).isAssignableFrom(value.getClass());
        super.setByParameter(parameter, value);
    }

}
