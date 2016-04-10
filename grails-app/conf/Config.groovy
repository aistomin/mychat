import com.icegreen.greenmail.util.ServerSetupTest

grails.project.groupId = appName

grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
    all          : '*/*',
    atom         : 'application/atom+xml',
    css          : 'text/css',
    csv          : 'text/csv',
    form         : 'application/x-www-form-urlencoded',
    html         : ['text/html', 'application/xhtml+xml'],
    js           : 'text/javascript',
    json         : ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss          : 'application/rss+xml',
    text         : 'text/plain',
    hal          : ['application/hal+json', 'application/hal+xml'],
    xml          : ['text/xml', 'application/xml']
]

grails.views.default.codec = "html"

grails.controllers.defaultScope = 'singleton'

grails.mail.port = ServerSetupTest.SMTP.port

grails.serverURL = 'http://localhost:8080/mychat'

grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml'
            codecs {
                expression = 'html'
                scriptlet = 'html'
                taglib = 'none'
                staticparts = 'none'
            }
        }
    }
}


grails.converters.encoding = "UTF-8"
grails.scaffolding.templates.domainSuffix = 'Instance'

grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.spring.bean.packages = []
grails.web.disable.multipart = false

grails.exceptionresolver.params.exclude = ['password']

grails.hibernate.cache.queries = false

grails.hibernate.pass.readonly = false
grails.hibernate.osiv.readonly = false

grails.gorm.failOnError = true
grails.gorm.autoFlush = true

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
    }
}

log4j.main = {
    error 'org.codehaus.groovy.grails.web.servlet',
        'org.codehaus.groovy.grails.web.pages',
        'org.codehaus.groovy.grails.web.sitemesh',
        'org.codehaus.groovy.grails.web.mapping.filter',
        'org.codehaus.groovy.grails.web.mapping',
        'org.codehaus.groovy.grails.commons',
        'org.codehaus.groovy.grails.plugins',
        'org.codehaus.groovy.grails.orm.hibernate',
        'org.springframework',
        'org.hibernate',
        'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.aistomin.chat.model.UserRecord'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.aistomin.chat.model.UserRecordUserRoleRecord'
grails.plugin.springsecurity.authority.className = 'org.aistomin.chat.model.UserRoleRecord'
grails.plugin.springsecurity.ui.register.defaultRoleNames = ['ROLE_USER']
grails.plugin.springsecurity.ui.register.postRegisterUrl = '/login'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    '/'                 : ['ROLE_USER'],
    '/index'            : ['ROLE_USER'],
    '/index.gsp'        : ['ROLE_USER'],
    '/assets/**'        : ['permitAll'],
    '/**/js/**'         : ['permitAll'],
    '/**/css/**'        : ['permitAll'],
    '/**/images/**'     : ['permitAll'],
    '/**/favicon.ico'   : ['permitAll'],
    '/atmosphere/chat/*': ['permitAll'],
    '/login/**'         : ['permitAll'],
    '/logout/**'        : ['permitAll'],
    '/register/**'      : ['permitAll'],
    '/greenmail/**'     : ['permitAll']
]

