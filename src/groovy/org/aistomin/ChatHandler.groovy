package org.aistomin

import grails.converters.JSON
import grails.util.Holders
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.cpr.AtmosphereResourceEvent
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.DefaultBroadcaster
import org.atmosphere.cpr.Meteor
import org.atmosphere.websocket.WebSocketEventListenerAdapter

/**
 * Created by aistomin on 20/02/16.
 */
class ChatHandler extends HttpServlet {

    private final def atmosphereMeteor = Holders.applicationContext.getBean("atmosphereMeteor")

    private final def chatService = Holders.applicationContext.getBean("chatService")

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/atmosphere/chat" + request.getPathInfo()
        Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping, true)
        Meteor m = Meteor.build(request)

        if (m.transport().equals(AtmosphereResource.TRANSPORT.WEBSOCKET)) {
            m.addListener(new WebSocketEventListenerAdapter() {
                @Override
                void onDisconnect(AtmosphereResourceEvent event) {
                    chatService.sendDisconnectMessage(event, request)
                }
            })
        } else {
            m.addListener(new AtmosphereResourceEventListenerAdapter() {
                @Override
                void onDisconnect(AtmosphereResourceEvent event) {
                    chatService.sendDisconnectMessage(event, request)
                }
            })
        }

        m.setBroadcaster(b)
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/atmosphere/chat" + request.getPathInfo();
        def jsonMap = JSON.parse(request.getReader().readLine().trim()) as Map
        String type = jsonMap.containsKey("type") ? jsonMap.type.toString() : null
        String message = jsonMap.containsKey("message") ? jsonMap.message.toString() : null

        if (type == null || message == null) {
            chatService.recordIncompleteMessage(jsonMap)
        } else {
            if (message.toLowerCase().contains("<script")) {
                chatService.recordMaliciousUseWarning(jsonMap)
            } else {
                chatService.recordChat(jsonMap)
                Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping)
                b.broadcast(jsonMap)
            }
        }
    }
}
