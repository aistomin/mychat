package org.aistomin.gorm

import grails.persistence.Entity

/**
 * User entity.
 */
@Entity
class UserRecord {

    /**
     * User's display name.
     */
    String name

    /**
     * User's username.
     */
    String username

    /**
     * Authentication password.
     */
    String password

    /**
     * Is user online now?
     */
    Boolean isOnline = false

    static constraints = {
        name blank: false, maxSize: 20
        username blank: false, maxSize: 15
    }
}
