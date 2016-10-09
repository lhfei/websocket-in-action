/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.lhfei.websocket.jsr356.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Aug 26, 2016
 */
@ClientEndpoint
public class ToUpper356ClientSocket {

	CountDownLatch latch = new CountDownLatch(1);
	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected to server");
		this.session = session;
		latch.countDown();
	}

	@OnMessage
	public void onText(String message, Session session) {
		System.out.println("Message received from server:" + message);
	}

	@OnClose
	public void onClose(CloseReason reason, Session session) {
		System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void sendMessage(String str) {
		try {
			session.getBasicRemote().sendText(str);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
