var stompClient = null;

var uri = document.URL;
var chatroomId = uri.substring(uri.lastIndexOf('/')+1);

var userId = $('#userId').val();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/sockjs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/message/' + chatroomId, function (greeting) {
            showChat(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/chats/" + chatroomId, {}, JSON.stringify({'userId':userId, 'message': $("#message").val()}));
}

function getDirection(user){
    if(user === true)   return "right";
    else if(user === false) return "left";
}

function showChat(message) {

    var localUser = false;

    if(parseInt(message.senderId) === parseInt(userId))
        localUser = true;
        
    console.log(message.senderId + " " + userId);
    console.log(localUser);

    $("#chatbox").append(`
        <li class="` + getDirection(localUser) + ` clearfix">
            <span class="chat-img pull-` + getDirection(localUser) + `">
                <img src="https://bootdey.com/img/Content/user_3.jpg" alt="User Avatar">
            </span>
            <div class="chat-body clearfix">
                <div class="header">
                    <strong class="primary-font">` + message.senderName  +`</strong>
                </div>
                <p>
                    ` + message.message + `
                </p>
            </div>
        </li>
        `);
}

$(document).ready(function(){
    userId = $('#userId').val();
    connect();
});

$(function () {
    $("form").on('submit', function (e) {
        $('#message').val('');
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});
