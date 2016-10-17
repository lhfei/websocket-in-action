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

package cn.lhfei.websocket.jetty.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Aug 26, 2016
 */

public class ZeppelinClient {
	public static void main(String[] args) {
		String url = "ws://master2.cloud.cn:8989/ws/";
		
		WebSocketClient client = null;
		try {
			client =  new WebSocketClient();
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			ZeppelinClientSocket socket = new ZeppelinClientSocket();
			URI uri = new URI(url);

			client.connect(socket, uri, request);
			
			socket.getLatch().await();
			socket.sendMessage("Hefei Li ....");
			socket.sendMessage("Test Zeppelin ....");
			Thread.sleep(10000l);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
