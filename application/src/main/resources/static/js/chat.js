let socket;
let roomId = prompt("Enter chat room ID:");

function connect() {
    if (roomId) {
        // Update the chat room title
        document.querySelector('h1').textContent = "Chat Room: " + roomId;

        if (socket == null || socket.readyState === WebSocket.CLOSED) {
            socket = new WebSocket("ws://localhost:8080/chat-websocket?roomId=" + roomId);

            socket.onopen = function () {
                console.log("WebSocket connection established to room " + roomId);
            };

            socket.onmessage = function (event) {
                const messageBox = document.getElementById("message-box");
                const newMessage = document.createElement("div");
                newMessage.textContent = event.data;
                newMessage.classList.add("message", "received");
                messageBox.appendChild(newMessage);
                messageBox.scrollTop = messageBox.scrollHeight;
            };

            socket.onclose = function (event) {
                console.log("WebSocket connection closed: ", event);
            };

            socket.onerror = function (error) {
                console.error("WebSocket error observed: ", error);
            };
        } else {
            console.log("WebSocket is already connected.");
        }
    } else {
        console.error("Room ID is required.");
    }
}

function sendMessage() {
    const messageInput = document.getElementById("message-input");
    const message = messageInput.value;

    if (message !== '' && socket.readyState === WebSocket.OPEN) {
        socket.send(message);

        const messageBox = document.getElementById("message-box");
        const sentMessage = document.createElement("div");
        sentMessage.textContent = "You: " + message;
        sentMessage.classList.add("message", "you");
        messageBox.appendChild(sentMessage);
        messageBox.scrollTop = messageBox.scrollHeight;

        messageInput.value = "";
    } else {
        console.error("WebSocket is not open. Ready state: " + socket.readyState);
    }
}

function disconnect() {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.close();
    }
}

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
}
