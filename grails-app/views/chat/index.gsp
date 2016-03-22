<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}

			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
        <asset:javascript src="atmosphere-meteor-jquery.js"/>
	</head>
	<body>
    <h1>Chat</h1>
    <div id="chat-window"></div>
    <label for="chat-input"></label><input id="chat-input" type="text"/>
    <script type="text/javascript">
        // required for IE console logging
        if (!window.console) console = {log: function () {
        }};

        /*
         The Jabber variable holds all JavaScript code required for communicating with the server.
         It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
         */
        var Jabber = {
            socket: null,
            chatSubscription: null,
            notificationSubscription: null,
            publicSubscription: null,
            transport: null,

            subscribe: function (options) {
                var defaults = {
                            type: '',
                            contentType: "application/json",
                            shared: false,
                            transport: 'websocket',
                            //transport: 'long-polling',
                            fallbackTransport: 'long-polling',
                            trackMessageLength: true
                        },
                        atmosphereRequest = $.extend({}, defaults, options);
                atmosphereRequest.onOpen = function (response) {
                    console.log('atmosphereOpen transport: ' + response.transport);
                };
                atmosphereRequest.onReconnect = function (request, response) {
                    console.log("atmosphereReconnect");
                };
                atmosphereRequest.onMessage = function (response) {
                    //console.log('onMessage: ' + response.responseBody);
                    Jabber.onMessage(response);
                };
                atmosphereRequest.onError = function (response) {
                    console.log('atmosphereError: ' + response);
                };
                atmosphereRequest.onTransportFailure = function (errorMsg, request) {
                    console.log('atmosphereTransportFailure: ' + errorMsg);
                };
                atmosphereRequest.onClose = function (response) {
                    console.log('atmosphereClose: ' + response);
                };
                switch (options.type) {
                    case 'chat':
                        Jabber.chatSubscription = Jabber.socket.subscribe(atmosphereRequest);
                        break;
                    case 'notification':
                        Jabber.notificationSubscription = Jabber.socket.subscribe(atmosphereRequest);
                        break;
                    case 'public':
                        Jabber.publicSubscription = Jabber.socket.subscribe(atmosphereRequest);
                        break;
                    default:
                        return false;
                }
            },

            unsubscribe: function () {
                Jabber.socket.unsubscribe();
                $('#chat-window').html('');
                $('#notification').html('');
                $('#public-update').html('');
                $('button').each(function () {
                    $(this).removeAttr('disabled');
                })
            },

            onMessage: function (response) {
                var data = response.responseBody;
                if ((message == '')) {
                    return;
                }
                console.log(data);
                var message = JSON.parse(data);
                var type = message.type;
                if (type == 'chat') {
                    var $chat = $('#chat-window');
                    $chat.append('<b>${currentUser.name}: </b>' + message.message + '<br/>');
                    $chat.scrollTop($chat.height());
                }
                if (type == 'notification') {
                    $('#notification').html(message.message);
                }
                if (type == 'public') {
                    $('#public-update').html(message.message);
                    if (message.message == 'Finished.') {
                        $('#public-trigger').removeAttr('disabled');
                    }
                }
            }
        };

        $(window).unload(function () {
            Jabber.unsubscribe();
        });

        $(document).ready(function () {
            if (typeof atmosphere == 'undefined') {
                // if using jquery.atmosphere.js
                Jabber.socket = $.atmosphere;
            } else {
                // if using atmosphere.js
                Jabber.socket = atmosphere;
            }

            var atmosphereRequest = {
                type: 'chat',
                url: 'atmosphere/chat/12345'
            };
            Jabber.subscribe(atmosphereRequest);

            $('#chat-input').keypress(function (event) {
                if (event.which === 13) {
                    event.preventDefault();
                    var data = {
                        type: 'chat',
                        message: $(this).val()
                    };
                    Jabber.chatSubscription.push(JSON.stringify(data));
                    $(this).val('');
                }
            });

            $('#notification-subscribe').on('click', function () {
                var atmosphereRequest = {
                    type: 'notification',
                    // note that the DefaultMeteorHandler uses the header below for setting and getting the broadcaster
                    headers: {'X-AtmosphereMeteor-Mapping': '/atmosphere/notification/userName'},
                    url: 'atmosphere/notification/userName'
                };
                Jabber.subscribe(atmosphereRequest);
                $(this).attr('disabled', 'disabled');
            });

            $('#notification-send').on('click', function () {
                var data = {
                    type: 'notification',
                    message: 'This is a notification message sent at ' + new Date() + '.'
                };
                Jabber.notificationSubscription.push(JSON.stringify(data));
            });

            $('#public-subscribe').on('click', function () {
                var atmosphereRequest = {
                    type: 'public',
                    url: 'atmosphere/public',
                    trackMessageLength: false
                };
                Jabber.subscribe(atmosphereRequest);
                $(this).attr('disabled', 'disabled');
            });

            $('#public-trigger').on('click', function () {
                $.ajax('atmosphereTest/triggerPublic');
                $(this).attr('disabled', 'disabled');
            });

            $('#unsubscribe').on('click', function () {
                Jabber.unsubscribe();
            });
        });
    </script>
	</body>
</html>
