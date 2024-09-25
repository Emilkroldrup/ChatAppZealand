let socket;
let currentRoom = null;

function loadRooms() {
    fetch("/rooms") // Assuming you're fetching available rooms from the backend
        .then(response => response.json())
        .then(rooms => {
            const roomList = document.getElementById("rooms");
            roomList.innerHTML = ''; // Clear the list first
            rooms.forEach(room => {
                const li = document.createElement("li");
                li.textContent = room.chatName;
                li.onclick = () => switchRoom(room.chatName);
                roomList.appendChild(li);
            });
        });
}

function switchRoom(roomName) {
    if (currentRoom === roomName) return; // Avoid switching to the same room
    disconnect(); // Disconnect from the previous room

    currentRoom = roomName;
    document.getElementById("room-title").textContent = "Chat Room: " + roomName;
    document.getElementById("message-input").disabled = false;
    document.querySelector("button[onclick='sendMessage()']").disabled = false;

    // Clear the message box when switching rooms
    const messageBox = document.getElementById("message-box");
    messageBox.innerHTML = ''; // Clear previous room's messages

    connectToRoom(roomName);
}

function connectToRoom(roomName) {
    socket = new WebSocket("ws://localhost:8080/chat-websocket?roomId=" + roomName);

    socket.onopen = function () {
        console.log("Connected to room: " + roomName);
    };

    socket.onmessage = function (event) {
        const messageBox = document.getElementById("message-box");
        const newMessage = document.createElement("div");
        newMessage.textContent = event.data;

        // Avoid displaying messages that the user has just sent themselves
        if (!newMessage.textContent.startsWith("You: ")) {
            newMessage.classList.add("message", "received");
            messageBox.appendChild(newMessage);
            messageBox.scrollTop = messageBox.scrollHeight; // Auto-scroll
        }
    };

    socket.onclose = function (event) {
        console.log("Disconnected from room: " + roomName);
    };

    socket.onerror = function (error) {
        console.error("WebSocket error: ", error);
    };
}

function sendMessage() {
    const messageInput = document.getElementById("message-input");
    const message = messageInput.value;

    if (message && socket && socket.readyState === WebSocket.OPEN) {
        // Send the message to the server
        socket.send(message);

        // Display the message on the client's side only (no broadcast needed)
        const messageBox = document.getElementById("message-box");
        const sentMessage = document.createElement("div");
        sentMessage.textContent = "You: " + message;
        sentMessage.classList.add("message", "you");
        messageBox.appendChild(sentMessage);
        messageBox.scrollTop = messageBox.scrollHeight; // Auto-scroll to bottom

        messageInput.value = ''; // Clear the input
    } else {
        console.error("WebSocket is not open or message is empty.");
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

window.onload = function() {
    // Load available chat rooms
    loadRooms();

    // Switch to "General" room automatically
    setTimeout(function () {
        switchRoom('General');
    }, 500); // Delay ensures WebSocket connects properly

    // Enable the input box and button after loading rooms
    document.getElementById("message-input").disabled = false;
    document.querySelector("button[onclick='sendMessage()']").disabled = false;
};
