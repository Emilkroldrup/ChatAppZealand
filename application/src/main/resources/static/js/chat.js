let socket;

function connect() {
    socket = new WebSocket("ws://localhost:8080/chat-websocket");

    socket.onmessage = function (event) {
        const messageBox = document.getElementById("message-box");
        const newMessage = document.createElement("div");
        newMessage.textContent = event.data;
        newMessage.classList.add("message", "received");
        messageBox.appendChild(newMessage);
        messageBox.scrollTop = messageBox.scrollHeight; // Auto-scroll to latest message
    };
}

function sendMessage() {
    const messageInput = document.getElementById("message-input");
    const message = messageInput.value;

    if (message !== '') {
        socket.send(message);

        const messageBox = document.getElementById("message-box");
        const sentMessage = document.createElement("div");
        sentMessage.textContent = "You: " + message;
        sentMessage.classList.add("message", "you");
        messageBox.appendChild(sentMessage);
        messageBox.scrollTop = messageBox.scrollHeight;  // Auto-scroll to bottom

        messageInput.value = "";
    }
}

function disconnect() {
    socket.close();
}

// Add event listener for Enter key to sent messages like any other chat application ;) 
document.addEventListener("DOMContentLoaded", function () {
    const messageInput = document.getElementById("message-input");
    messageInput.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    });
});
