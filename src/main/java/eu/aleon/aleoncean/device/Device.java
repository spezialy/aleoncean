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
package eu.aleon.aleoncean.device;

import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.RadioPacket;
import java.util.Set;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public interface Device extends Comparable<Device> {

    public void parseRadioPacket(final RadioPacket packet);

    public Set<DeviceParameter> getParameters();

    public Object getByParameter(final DeviceParameter parameter) throws IllegalArgumentException;

    public void setByParameter(final DeviceParameter parameter, final Object value) throws IllegalArgumentException;

    /**
     * Get the EnOcean address of the remote / real device.
     *
     * @return Return the address of the remote / real device.
     */
    public EnOceanId getAddressRemote();

    /**
     * Get the EnOcean address of our self.
     *
     * @return Return the address of our self, the local one.
     */
    public EnOceanId getAddressLocal();

    public void addParameterUpdatedListener(DeviceParameterUpdatedListener listener);

    public void removeParameterUpdatedListener(DeviceParameterUpdatedListener listener);

}
