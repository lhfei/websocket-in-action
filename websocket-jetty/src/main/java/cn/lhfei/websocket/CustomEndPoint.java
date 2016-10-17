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

package cn.lhfei.websocket;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Aug 26, 2016
 */
@ServerEndpoint("/ratesrv")
public class CustomEndPoint {
	// queue holds the list of connected clients
	private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();
	private static Thread rateThread; // rate publisher thread
	static {
		// rate publisher thread, generates a new value for USD rate every 2
		// seconds.
		rateThread = new Thread() {
			public void run() {
				DecimalFormat df = new DecimalFormat("#.####");
				while (true) {
					double d = 2 + Math.random();
					if (queue != null)
						sendAll("USD Rate: " + df.format(d));
					try {
						sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			};
		};
		rateThread.start();
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		// provided for completeness, in out scenario clients don't send any
		// msg.
		try {
			System.out.println("received msg " + msg + " from " + session.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnOpen
	public void open(Session session) {
		queue.add(session);
		System.out.println("New session opened: " + session.getId());
	}

	@OnError
	public void error(Session session, Throwable t) {
		queue.remove(session);
		System.err.println("Error on session " + session.getId());
	}

	@OnClose
	public void closedConnection(Session session) {
		queue.remove(session);
		System.out.println("session closed: " + session.getId());
	}

	private static void sendAll(String msg) {
		try {
			/* Send the new rate to all open WebSocket sessions */
			ArrayList<Session> closedSessions = new ArrayList<>();
			for (Session session : queue) {
				if (!session.isOpen()) {
					System.err.println("Closed session: " + session.getId());
					closedSessions.add(session);
				} else {
					session.getBasicRemote().sendText(msg);
				}
			}
			queue.removeAll(closedSessions);
			System.out.println("Sending " + msg + " to " + queue.size() + " clients");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
