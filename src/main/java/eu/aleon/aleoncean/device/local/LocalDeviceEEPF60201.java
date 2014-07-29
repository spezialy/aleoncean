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
package eu.aleon.aleoncean.device.local;

import eu.aleon.aleoncean.device.DeviceEEPF60201;
import eu.aleon.aleoncean.device.LocalDevice;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.rxtx.ESP3Connector;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class LocalDeviceEEPF60201 extends DeviceEEPF60201 implements LocalDevice {

    public LocalDeviceEEPF60201(ESP3Connector conn, EnOceanId addressRemote, EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

}
