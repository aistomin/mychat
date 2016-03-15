package org.aistomin.chat.model

import grails.persistence.Entity

@Entity
class MessageRecord {

    /**
     * Message's text.
     */
    String text

    static belongsTo = [chat: ChatRecord]

    static constraints = {
        text blank: false, maxSize: 255
    }
}
