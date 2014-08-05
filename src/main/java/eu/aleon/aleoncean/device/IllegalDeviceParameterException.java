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
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class IllegalDeviceParameterException extends Exception {

    private static final long serialVersionUID = -7446981762480106959L;

    public IllegalDeviceParameterException() {
    }

    public IllegalDeviceParameterException(String message) {
        super(message);
    }

    public IllegalDeviceParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDeviceParameterException(Throwable cause) {
        super(cause);
    }

    public IllegalDeviceParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
