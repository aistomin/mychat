import org.aistomin.chat.model.UserRecord
import org.aistomin.chat.model.UserRecordUserRoleRecord
import org.aistomin.chat.model.UserRoleRecord

class BootStrap {

    def init = { servletContext ->
        if (UserRoleRecord.count == 0) {
            new UserRoleRecord(authority: 'ROLE_USER').save()
        }
        if (UserRecord.count == 0) {
            final def user = new UserRecord(
                name: 'My User', username: 'test', password: 'test'
            ).save()
            new UserRecordUserRoleRecord(
                user, UserRoleRecord.findByAuthority('ROLE_USER')
            ).save()
        }
    }

    def destroy = {
    }
}
