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

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @since Aug 26, 2016
 */
@WebServlet(urlPatterns="/toUpper")
public class ToUpperWebSocketServlet  extends WebSocketServlet{

	@Override
	public void configure(WebSocketServletFactory factory) {
		
	      factory.register(ToUpperWebSocket.class);
		
	}

}
