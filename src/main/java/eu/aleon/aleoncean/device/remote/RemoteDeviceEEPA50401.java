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
import eu.aleon.aleoncean.device.RemoteDevice;
import eu.aleon.aleoncean.device.StandardDevice;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.RadioPacket;
import eu.aleon.aleoncean.packet.radio.RadioPacket4BS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataEEPA50401;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.rxtx.ESP3Connector;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RemoteDeviceEEPA50401 extends StandardDevice implements RemoteDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDeviceEEPA50401.class);

    private Double humidity;
    private Double temperature;

    public RemoteDeviceEEPA50401(final ESP3Connector conn,
                                 final EnOceanId addressRemote,
                                 final EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(final DeviceParameterUpdatedInitiation initiation, final Double humidity) {
        final Double oldHumidity = this.humidity;
        this.humidity = humidity;
        fireParameterChanged(DeviceParameter.HUMIDITY_PERCENT, initiation, oldHumidity, humidity);
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(final DeviceParameterUpdatedInitiation initiation, final Double temperature) {
        final Double oldTemperature = this.temperature;
        this.temperature = temperature;
        fireParameterChanged(DeviceParameter.TEMPERATURE_CELSIUS, initiation, oldTemperature, temperature);
    }

    private void parseRadioPacket4BS(RadioPacket4BS packet) {
        if (packet.isTeachIn()) {
            LOGGER.debug("Ignore teach-in packets.");
            return;
        }

        final UserDataEEPA50401 userData = new UserDataEEPA50401(packet.getUserDataRaw());
        try {
            setHumidity(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getHumidity());
        } catch (UserDataScaleValueException ex) {
            LOGGER.warn("Received humidity is invalid.");
        }

        try {
            setTemperature(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getTemperature());
        } catch (UserDataScaleValueException ex) {
            LOGGER.warn("Received temperature is invalid.");
        }
    }

    @Override
    public void parseRadioPacket(RadioPacket packet) {
        if (packet instanceof RadioPacket4BS) {
            parseRadioPacket4BS((RadioPacket4BS) packet);
        } else {
            LOGGER.warn("Don't know how to handle radio choice 0x%02X.", packet.getChoice());
        }
    }

    @Override
    protected void fillParameters(final Set<DeviceParameter> params) {
        params.add(DeviceParameter.HUMIDITY_PERCENT);
        params.add(DeviceParameter.TEMPERATURE_CELSIUS);
    }

    @Override
    public Object getByParameter(DeviceParameter parameter) throws IllegalArgumentException {
        switch (parameter) {
            case HUMIDITY_PERCENT:
                return getHumidity();
            case TEMPERATURE_CELSIUS:
                return getTemperature();
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
