
var  websocket = new WebSocket("ws://localhost:8181/sessions", "ws");

function onMessage(message) {
    console.log("onMessage: " + message)
}

websocket.onopen = function() { console.log("ws opened"); }
websocket.onerror = function() { console.log("ws error"); }
websocket.onmessage = function(e) { onMessage(e.data); }
websocket.onclose = function() { console.log("ws closed"); }

function send(message) {
    websocket.send(message);
}
