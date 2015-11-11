var wsocket;
	//var serviceLocation = "ws://192.168.1.1:8080/hascode/chat/";
	
	var serviceLocation = "ws://localhost:8080/SAPo-FrontOffice/chat/";
	
	var $nickName;
	var $message;
	var $chatWindow;
	var $aux;
	var room = '';
 
	function onMessageReceived(evt) {
		//$('.chat-wrapper').show();
		//var msg = eval('(' + evt.data + ')');
		var msg = JSON.parse(evt.data); // native API

		var $messageLine = $('<tr > <td  > <h4><span class="label label-default">' + msg.received +'</span></h4>'
				+ '</td> <td  ><h4><span class="label label-default">'+ msg.sender +'</span></h4>'
				+ '</td> <td  ><h4><span class="label label-default">' + msg.message +'</span></h4>'
				+ '</td></tr>');
		$chatWindow.append($messageLine);
	
		//$chatWindow.prepend($messageLine);

	}
	
	function sendMessage() {
		var msg = '{"message":"' + $message.val() + '", "sender":"'
				+ $nickName.val() + '", "received":""}';
		wsocket.send(msg);
		
		//$('.chat-wrapper').show();
		$message.val('').focus();
	}
 
	function connectToChatserver() {
		//room = $('#chatroom option:selected').val();
		room = $('#chatroom2').val();
		wsocket = new WebSocket(serviceLocation + room);
		wsocket.onmessage = onMessageReceived;
	}
 
	function leaveRoom() {
		wsocket.close();
		$chatWindow.empty();
		$('.chat-wrapper').hide();
		$('.chat-signin').show();
		$nickName.focus();
	}
 
	$(document).ready(function() {
		$nickName = $('#nickname');
		$message = $('#message');
		$chatWindow = $('#response');
		$('.chat-wrapper').hide();
		//$('.chat-wrapper').show();
		$nickName.focus();
 
		$('#enterRoom').click(function(evt) {
			evt.preventDefault();
			connectToChatserver();
			$('.chat-wrapper h2').text('Chat # '+$nickName.val() + "@" + room);
			$('.chat-signin').hide();
			$('.chat-wrapper').show();
			$message.focus();
		});
		$('#do-chat').submit(function(evt) {
			evt.preventDefault();
			sendMessage()
		});
 
		$('#leave-room').click(function(){
			leaveRoom();
		});
	});