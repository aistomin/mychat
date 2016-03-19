import org.aistomin.chat.ChatHandler

defaultMapping = "/atmosphere/*"

def defaultInitParams = [
    "org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
    "org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.client.TrackMessageSizeInterceptor,
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]
servlets = [
    MeteorServletChat: [
        className : "org.aistomin.chat.ChatServlet",
        mapping   : "/atmosphere/chat/*",
        handler   : ChatHandler,
        initParams: defaultInitParams
    ]
]