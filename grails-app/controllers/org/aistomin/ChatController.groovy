package org.aistomin

import grails.plugin.springsecurity.annotation.Secured

/**
 * Main chat controller.
 */
class ChatController {

    /**
     * Atmosphere service.
     */
    def atmosphereMeteor

    /**
     * Chat service.
     */
    def chatService

    /**
     * Open index page.
     * @return rendered page.
     */
    @Secured(['ROLE_USER'])
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

    @Secured(['ROLE_USER'])
    def triggerPublic() {
        chatService.triggerPublic()
        render "success"
    }
}
