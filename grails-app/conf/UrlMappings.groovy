class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
            }
        }

        "/"(controller: 'chat', action: 'index')
        "500"(view: '/error')
    }
}
