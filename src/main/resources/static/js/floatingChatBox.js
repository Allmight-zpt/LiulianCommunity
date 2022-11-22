var element = $('.floating-chat');
var myStorage = localStorage;
var isConnect = false;
if (!myStorage.getItem('chatID')) {
    myStorage.setItem('chatID', createUUID());
}

setTimeout(function() {
    element.addClass('enter');
}, 1000);

element.click(openElement);

function openElement() {
    if(!isConnect){
        var accountId = document.getElementsByClassName('floating-chat')[0].getAttribute('data-accountId');
        var username = document.getElementsByClassName('floating-chat')[0].getAttribute('data-username');
        connect(accountId,username);
        isConnect = true;
    }
    var messages = element.find('.messages');
    var textInput = element.find('.text-box');
    element.find('>i').hide();
    element.addClass('expand');
    element.find('.chat').addClass('enter');
    var strLength = textInput.val().length * 2;
    textInput.keydown(onMetaAndEnter).prop("disabled", false).focus();
    element.off('click', openElement);
    element.find('.header button').click(closeElement);
    element.find('#sendMessage').click(sendNewMessage);
    messages.scrollTop(messages.prop("scrollHeight"));
}

function connect(accountId,username){
    if ('WebSocket' in window){
        ws = new WebSocket("ws://localhost:8082/socketserver/"+accountId+"?username="+username);
    }
    else if ('MozWebSocket' in window){
        ws = new WebSocket("ws://localhost:8082/socketserver/"+accountId);
    }
    else{
        alert("该浏览器不支持websocket");
    }
    ws.onmessage = function(evt) {
        var messagesContainer = $('.messages');
        messagesContainer.append([
            '<li class="other">',
            evt.data,
            '</li>'
        ].join(''));
        messagesContainer.finish().animate({
            scrollTop: messagesContainer.prop("scrollHeight")
        }, messagesContainer.scrollHeight);
    };

    ws.onclose = function(evt) {
        var messagesContainer = $('.messages');
        messagesContainer.append([
            '<li class="other">',
            '【系统消息】连接关闭',
            '</li>'
        ].join(''));
    };

    ws.onopen = function(evt) {
        //连接成功 先获取历史聊天记录
        var messagesContainer = $('.messages');
        $.ajax({
            type:"GET",
            url:"/chatData/" + accountId,
            contentType:"application/json",
            success:function (response){
                if(response.code == 200){
                    //历史记录不为空
                    for (let i = 0; i <response.data.length; i++) {
                        var meg = response.data[i].split(":");
                        var source = meg[0];
                        var content = meg[1];
                        if(source === "server"){
                            messagesContainer.append([
                                '<li class="other">',
                                content,
                                '</li>'
                            ].join(''));
                        }else{
                            messagesContainer.append([
                                '<li class="self">',
                                content,
                                '</li>'
                            ].join(''));
                        }
                    }
                    //滚轮滑倒最底部
                    messagesContainer.finish().animate({
                        scrollTop: messagesContainer.prop("scrollHeight")
                    }, messagesContainer.scrollHeight);
                }
            },
            dataType:"json"
        })
    };
}

function closeElement() {
    element.find('.chat').removeClass('enter').hide();
    element.find('>i').show();
    element.removeClass('expand');
    element.find('.header button').off('click', closeElement);
    element.find('#sendMessage').off('click', sendNewMessage);
    element.find('.text-box').off('keydown', onMetaAndEnter).prop("disabled", true).blur();
    setTimeout(function() {
        element.find('.chat').removeClass('enter').show()
        element.click(openElement);
    }, 500);
}

function createUUID() {
    // http://www.ietf.org/rfc/rfc4122.txt
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

function sendNewMessage() {
    var userInput = $('.text-box');
    var newMessage = userInput.html().replace(/\<div\>|\<br.*?\>/ig, '\n').replace(/\<\/div\>/g, '').trim().replace(/\n/g, '<br>');
    if (!newMessage) return;
    var messagesContainer = $('.messages');
    messagesContainer.append([
        '<li class="self">',
        newMessage,
        '</li>'
    ].join(''));

    // clean out old message
    userInput.html('');
    // focus on input
    userInput.focus();

    messagesContainer.finish().animate({
        scrollTop: messagesContainer.prop("scrollHeight")
    }, 250);

    ws.send(newMessage);
}

function onMetaAndEnter(event) {
    if ((event.metaKey || event.ctrlKey) && event.keyCode == 13) {
        sendNewMessage();
    }
}