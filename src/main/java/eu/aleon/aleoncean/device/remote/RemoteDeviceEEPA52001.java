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
import eu.aleon.aleoncean.device.IllegalDeviceParameterException;
import eu.aleon.aleoncean.device.RemoteDevice;
import eu.aleon.aleoncean.device.StandardDevice;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.RadioPacket;
import eu.aleon.aleoncean.packet.radio.RadioPacket4BS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.packet.radio.userdata.eepa520.Function;
import eu.aleon.aleoncean.packet.radio.userdata.eepa520.SetPointSelection;
import eu.aleon.aleoncean.packet.radio.userdata.eepa520.UserDataEEPA52001FromActuator;
import eu.aleon.aleoncean.packet.radio.userdata.eepa520.UserDataEEPA52001ToActuator;
import eu.aleon.aleoncean.rxtx.ESP3Connector;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class RemoteDeviceEEPA52001 extends StandardDevice implements RemoteDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDeviceEEPA52001.class);

    private Integer lastReceivedValvePosition;
    private Integer valvePositionToSend;

    // private Integer lastReceivedTemperature;
    // private Integer temperatureSetPointToSend;
    // private Integer temperatureCurrentToSend;
    private final Boolean temperatureControlled = false;

    public RemoteDeviceEEPA52001(final ESP3Connector conn,
                                 final EnOceanId addressRemote,
                                 final EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

    public Integer getLastValvePosition() {
        return lastReceivedValvePosition;
    }

    public void setLastValvePosition(final DeviceParameterUpdatedInitiation initiation,
                                     final Integer valvePosition) {
        final Integer oldValvePosition = this.lastReceivedValvePosition;
        this.lastReceivedValvePosition = valvePosition;
        fireParameterChanged(DeviceParameter.VALVE_POS_NEW, initiation, oldValvePosition, valvePosition);
    }

    public Integer getNewValvePosition() {
        return valvePositionToSend;
    }

    public void setNewValvePosition(final DeviceParameterUpdatedInitiation initiation,
                                    final Integer valvePosition) {
        final Integer oldValvePosition = this.valvePositionToSend;
        this.valvePositionToSend = valvePosition;
        fireParameterChanged(DeviceParameter.VALVE_POS_NEW, initiation, oldValvePosition, valvePosition);
    }

    private void sendPacket() {
        if (valvePositionToSend == null
            || temperatureControlled == null) {
            return;
        }

        final UserDataEEPA52001ToActuator userData = new UserDataEEPA52001ToActuator();

        try {
            if (temperatureControlled) {
                userData.setSetPointSelection(SetPointSelection.TEMPERATURE);
                // userData.setTemperatureSetpoint(temperature);
                return;
            } else {
                userData.setSetPointSelection(SetPointSelection.VALVE_POSITION);
                userData.setValvePosition(valvePositionToSend);
            }
            userData.setRunInitSequence(false);
            userData.setLiftSet(false);
            userData.setValveOpen(false);
            userData.setValveClosed(false);
            userData.setEnergyConsumotionReduced(false);
            userData.setSetPointInverse(false);
            userData.setFunction(Function.RCU);
        } catch (UserDataScaleValueException ex) {
            LOGGER.warn("Fill user data failed.\n{}", ex);
            return;
        }

        send(userData);
    }

    private void parseRadioPacket4BS(final RadioPacket4BS packet) {
        if (packet.isTeachIn()) {
            LOGGER.debug("Ignore teach-in packets.");
            return;
        }

        final UserDataEEPA52001FromActuator userData = new UserDataEEPA52001FromActuator(packet.getUserDataRaw());
        try {
            setLastValvePosition(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getCurrentValue());
        } catch (UserDataScaleValueException ex) {
            LOGGER.warn("Received position is invalid.");
        }

        sendPacket();
    }

    @Override
    public void parseRadioPacket(final RadioPacket packet) {
        if (packet instanceof RadioPacket4BS) {
            parseRadioPacket4BS((RadioPacket4BS) packet);
        } else {
            LOGGER.warn("Don't know how to handle radio choice 0x%02X.", packet.getChoice());
        }
    }

    @Override
    protected void fillParameters(final Set<DeviceParameter> params) {
        params.add(DeviceParameter.VALVE_POS_LAST);
        params.add(DeviceParameter.VALVE_POS_NEW);
    }

    @Override
    public Object getByParameter(final DeviceParameter parameter) throws IllegalDeviceParameterException {
        switch (parameter) {
            case VALVE_POS_LAST:
                return getLastValvePosition();
            case VALVE_POS_NEW:
                return getNewValvePosition();
            default:
                return super.getByParameter(parameter);
        }
    }

    @Override
    public void setByParameter(final DeviceParameter parameter, final Object value)
            throws IllegalDeviceParameterException {
        assert DeviceParameter.getSupportedClass(parameter).isAssignableFrom(value.getClass());
        switch (parameter) {
            case VALVE_POS_NEW:
                setNewValvePosition(DeviceParameterUpdatedInitiation.SET_PARAMETER, (Integer) value);
                break;
            default:
                super.setByParameter(parameter, value);
                break;
        }
    }

}
