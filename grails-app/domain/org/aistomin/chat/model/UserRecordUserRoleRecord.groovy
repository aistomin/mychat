package org.aistomin.chat.model

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache = true, includeNames = true, includePackage = false)
class UserRecordUserRoleRecord implements Serializable {

    private static final long serialVersionUID = 1

    UserRecord userRecord
    UserRoleRecord userRoleRecord

    UserRecordUserRoleRecord(UserRecord u, UserRoleRecord r) {
        this()
        userRecord = u
        userRoleRecord = r
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserRecordUserRoleRecord)) {
            return false
        }

        other.userRecord?.id == userRecord?.id && other.userRoleRecord?.id == userRoleRecord?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (userRecord) builder.append(userRecord.id)
        if (userRoleRecord) builder.append(userRoleRecord.id)
        builder.toHashCode()
    }

    static UserRecordUserRoleRecord get(long userRecordId, long userRoleRecordId) {
        criteriaFor(userRecordId, userRoleRecordId).get()
    }

    static boolean exists(long userRecordId, long userRoleRecordId) {
        criteriaFor(userRecordId, userRoleRecordId).count()
    }

    private static DetachedCriteria criteriaFor(long userRecordId, long userRoleRecordId) {
        UserRecordUserRoleRecord.where {
            userRecord == UserRecord.load(userRecordId) &&
                userRoleRecord == UserRoleRecord.load(userRoleRecordId)
        }
    }

    static UserRecordUserRoleRecord create(
        UserRecord userRecord, UserRoleRecord userRoleRecord,
        boolean flush = false
    ) {
        def instance = new UserRecordUserRoleRecord(userRecord: userRecord, userRoleRecord: userRoleRecord)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(UserRecord u, UserRoleRecord r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = UserRecordUserRoleRecord.where { userRecord == u && userRoleRecord == r }.deleteAll()

        if (flush) {
            UserRecordUserRoleRecord.withSession { it.flush() }
        }

        rowCount
    }

    static void removeAll(UserRecord u, boolean flush = false) {
        if (u == null) return

        UserRecordUserRoleRecord.where { userRecord == u }.deleteAll()

        if (flush) {
            UserRecordUserRoleRecord.withSession { it.flush() }
        }
    }

    static void removeAll(UserRoleRecord r, boolean flush = false) {
        if (r == null) return

        UserRecordUserRoleRecord.where { userRoleRecord == r }.deleteAll()

        if (flush) {
            UserRecordUserRoleRecord.withSession { it.flush() }
        }
    }

    static constraints = {
        userRoleRecord validator: { UserRoleRecord r, UserRecordUserRoleRecord ur ->
            if (ur.userRecord == null || ur.userRecord.id == null) return
            boolean existing = false
            UserRecordUserRoleRecord.withNewSession {
                existing = UserRecordUserRoleRecord.exists(ur.userRecord.id, r.id)
            }
            if (existing) {
                return 'userRole.exists'
            }
        }
    }

    static mapping = {
        id composite: ['userRecord', 'userRoleRecord']
        version false
    }
}
