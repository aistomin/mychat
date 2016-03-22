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
     * Spring security service.
     */
    def springSecurityService

    /**
     * Open index page.
     * @return rendered page.
     */
    @Secured(['ROLE_USER'])
    def index() {
        println "ChatController.index"
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
        assert springSecurityService.isLoggedIn()
        final def user = springSecurityService.getCurrentUser()
        assert user
        println "user = $user"
        println "user = $user"
        render(view: "/chat/index", model: [currentUser: user])
    }

    @Secured(['ROLE_USER'])
    def triggerPublic() {
        chatService.triggerPublic()
        render "success"
    }
}
