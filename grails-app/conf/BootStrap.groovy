import org.aistomin.chat.model.UserRecord
import org.aistomin.chat.model.UserRecordUserRoleRecord
import org.aistomin.chat.model.UserRoleRecord

class BootStrap {

    def init = { servletContext ->
        if (UserRoleRecord.count == 0) {
            new UserRoleRecord(authority: 'ROLE_USER').save()
        }
        if (UserRecord.count == 0) {
            new UserRecordUserRoleRecord(
                new UserRecord(
                    name: 'My User', username: 'test', password: 'test'
                ).save(), UserRoleRecord.findByAuthority('ROLE_USER')
            ).save(flush: true)
        }
        assert UserRecord.count() == 1
        assert UserRecordUserRoleRecord.count() == 1
        assert UserRoleRecord.count() == 1
    }

    def destroy = {
    }
}
