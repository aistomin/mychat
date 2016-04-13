package org.aistomin.chat.model

import grails.persistence.Entity

/**
 * Chat entity.
 */
@Entity
class ChatRecord {

    /**
     * Chat's title.
     */
    String title

    /**
     * Chat's owner.
     */
    UserRecord owner

    static hasMany = [
        messages: MessageRecord,
        members : UserRecord
    ]
    static constraints = {
        title blank: false, maxSize: 20
    }
}
