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
package eu.aleon.aleoncean.packet;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class ESP3Timeout {

    /**
     * Timeout between two characters (ms).
     */
    public static final int CHARS = 100;

    /**
     * Timeout between request / event and reponse (ms).
     */
    public static final int RESPONSE = 500;

    private ESP3Timeout() {
    }

}
