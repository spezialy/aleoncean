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

import eu.aleon.aleoncean.device.local.LocalDeviceEEPF60201;
import eu.aleon.aleoncean.device.local.LocalDeviceEEPF60202;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPA50205;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPA50401;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPA50802;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPD20108;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPF60201;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPF60202;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPF61000;
import eu.aleon.aleoncean.device.remote.RemoteDeviceEEPF61001;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.rxtx.ESP3Connector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this factory to create devices by their identifier.
 *
 * The format of the identifier is defined as:
 * RD (remote device) or LD (local device) _ EEP (separator is a dash character).
 * So, for example LD_F6-02-01, RD_D2-01-08, ...
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class DeviceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceFactory.class);

    private static final String LD_F60201 = "LD_F6-02-01";
    private static final String LD_F60202 = "LD_F6-02-02";
    private static final String RD_A50205 = "RD_A5-02-05";
    private static final String RD_A50401 = "RD_A5-04-01";
    private static final String RD_A50802 = "RD_A5-08-02";
    private static final String RD_D20108 = "RD_D2-01-08";
    private static final String RD_F60201 = "RD_F6-02-01";
    private static final String RD_F60202 = "RD_F6-02-02";
    private static final String RD_F61000 = "RD_F6-10-00";
    private static final String RD_F61001 = "RD_F6-10-01";

    private static final NavigableMap<String, Class<? extends StandardDevice>> MAP = new TreeMap<>();

    static {
        MAP.put(LD_F60201, LocalDeviceEEPF60201.class);
        MAP.put(LD_F60202, LocalDeviceEEPF60202.class);
        MAP.put(RD_A50205, RemoteDeviceEEPA50205.class);
        MAP.put(RD_A50401, RemoteDeviceEEPA50401.class);
        MAP.put(RD_A50802, RemoteDeviceEEPA50802.class);
        MAP.put(RD_D20108, RemoteDeviceEEPD20108.class);
        MAP.put(RD_F60201, RemoteDeviceEEPF60201.class);
        MAP.put(RD_F60202, RemoteDeviceEEPF60202.class);
        MAP.put(RD_F61000, RemoteDeviceEEPF61000.class);
        MAP.put(RD_F61001, RemoteDeviceEEPF61001.class);
    }

    public static boolean isTypeSupported(final String type) {
        return MAP.containsKey(type);
    }

    public static StandardDevice createFromClass(Class<? extends StandardDevice> clazz,
                                                 final ESP3Connector connector,
                                                 final EnOceanId addressRemote,
                                                 final EnOceanId addressLocal) {
        Constructor<? extends StandardDevice> deviceConstructor;
        try {
            deviceConstructor = clazz.getConstructor(ESP3Connector.class, EnOceanId.class, EnOceanId.class);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("Device constructor not found.");
            return null;
        } catch (SecurityException e) {
            LOGGER.warn("Search for device constructor throws security exception.");
            return null;
        }
        try {
            return deviceConstructor.newInstance(connector, addressRemote, addressLocal);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.warn("Device creation failed", ex);
            return null;
        }
    }

    public static StandardDevice createFromType(final String type,
                                                final ESP3Connector connector,
                                                final EnOceanId addressRemote,
                                                final EnOceanId addressLocal) {
        final Class<? extends StandardDevice> clazz = MAP.get(type);
        if (clazz != null) {
            return createFromClass(clazz, connector, addressRemote, addressLocal);
        } else {
            return null;
        }
    }

    public static Class<? extends StandardDevice> getClassForType(final String type) {
        return MAP.get(type);
    }

    private DeviceFactory() {
    }
}
