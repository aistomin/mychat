package org.aistomin

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
