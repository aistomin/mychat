package org.aistomin

import grails.plugin.springsecurity.annotation.Secured

class ChatController {

    def atmosphereMeteor
    def chatService

    def index() {
        if (!atmosphereMeteor.broadcasterFactory) {
            throw new RuntimeException(
                "atmosphereMeteor.broadcasterFactory is null"
            )
        }
        if (!atmosphereMeteor.framework) {
            throw new RuntimeException(
                "atmosphereMeteor.framework is null"
            )
        }
        render(view: "/index")
    }

    def triggerPublic() {
        chatService.triggerPublic()
        render "success"
    }
}
