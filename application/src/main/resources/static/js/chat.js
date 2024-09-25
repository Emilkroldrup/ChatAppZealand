let socket;

function connect() {
    if (socket == null || socket.readyState === WebSocket.CLOSED) {
        socket = new WebSocket("ws://localhost:8080/chat-websocket");

        socket.onopen = function () {
            console.log("WebSocket connection established");
        };

        socket.onmessage = function (event) {
            const messageBox = document.getElementById("message-box");
            const newMessage = document.createElement("div");
            newMessage.textContent = event.data;
            newMessage.classList.add("message", "received");
            messageBox.appendChild(newMessage);
            messageBox.scrollTop = messageBox.scrollHeight; // Auto-scroll to latest message
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
        messageBox.scrollTop = messageBox.scrollHeight;  // Auto-scroll to bottom

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

