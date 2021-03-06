/* 
 * Copyright (c) 2009, 2012 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */
package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;


/**
 * An on-the-wire representation of an MQTT SUBACK.
 */
public class MqttSuback extends MqttAck {
	private int[] grantedQos;		// Not currently made available to anyone. 
	
	public MqttSuback(byte info, byte[] data) throws Exception {
		super(MqttWireMessage.MESSAGE_TYPE_SUBACK);
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		msgId = dis.readLong();
		int index = 0;
		grantedQos = new int[data.length-2];
		int qos = dis.read();
		while (qos != -1 && index < grantedQos.length) {
			grantedQos[index] = qos;
			index++;
			qos = dis.read();
		}
		dis.close();
	}
	
	protected byte[] getVariableHeader() throws MqttException {
		// Not needed, as the client never encodes a SUBACK
		return new byte[0];
	}
}
