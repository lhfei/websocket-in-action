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

package cn.lhfei.websocket.jetty.server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Aug 26, 2016
 */
@WebSocket
public class ToUpperWebSocket {
	private Session session;
	private long index = 1l;
	
	@OnWebSocketMessage
	public void onText(Session session, String message) throws IOException {
		System.out.println("Message received:" + message);
		if (session.isOpen()) {
			String response = message.toUpperCase();
			session.getRemote().sendString(response);
		}
	}

	@OnWebSocketConnect
	public void onConnect(Session session) throws IOException {
		this.session = session;
		System.out.println(session.getRemoteAddress().getHostString() + " connected!");
		
		/*try {
			while(true){
				session.getRemote().sendString("==" +index+ "==: Hefei Li");
				
				Thread.sleep(2000l);
				
				index++;
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
	public void pushMessage(String message) {
		if(null != session && session.isOpen()){
			try {
				session.getRemote().sendString(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@OnWebSocketClose
	public void onClose(Session session, int status, String reason) {
		System.out.println(session.getRemoteAddress().getHostString() + " closed!");
		try {
			this.session.disconnect();
		} catch (IOException e) {
		}
	}

}
