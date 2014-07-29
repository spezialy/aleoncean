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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Markus Rathgeb <maggu2810@gmail.com>
 */
public class SmartAckCommandPacket extends ESP3Packet {

    private static final int POS_CODE = DATA_START;

    private byte smartAckCode;

    private byte[] smartAckData;

    public SmartAckCommandPacket() {
        this(SmartAckCode.SA_UNDEF);
    }

    public SmartAckCommandPacket(final byte smartAckCode) {
        super(PacketType.SMART_ACK_COMMAND);
        setSmartAckCode(smartAckCode);
    }

    @Override
    public void setData(byte[] data) {
        int pos = 0;
        byte[] tmp;

        setSmartAckCode(data[pos++]);

        tmp = new byte[data.length - pos];
        System.arraycopy(data, pos, tmp, 0, tmp.length);
        setSmartAckData(tmp);
        //pos += tmp.length;
    }

    @Override
    public byte[] getData() {
        byte[] rawSmartAckData = getSmartAckData();
        if (rawSmartAckData == null) {
            rawSmartAckData = new byte[0];
        }

        final int rawDataLength = 1 /* smart ack code */
                                  + rawSmartAckData.length;

        final byte[] rawData = new byte[rawDataLength];
        ByteBuffer bb = ByteBuffer.wrap(rawData);
        bb.order(ByteOrder.BIG_ENDIAN);

        bb.put(getSmartAckCode());
        bb.put(rawSmartAckData);

        return rawData;
    }

    public byte getSmartAckCode() {
        return smartAckCode;
    }

    public final void setSmartAckCode(byte smartAckCode) {
        this.smartAckCode = smartAckCode;
    }

    public byte[] getSmartAckData() {
        return smartAckData;
    }

    public void setSmartAckData(byte[] smartAckData) {
        this.smartAckData = smartAckData;
    }

    public static byte getSmartAckCode(final byte[] raw) {
        return raw[POS_CODE];
    }
}
