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

/**
 * Devices, that will be communicated with.
 *
 * All device classes, that handle a device they would communicate with (not simulate it) should implement this
 * interface.
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public interface RemoteDevice extends Device {

}
