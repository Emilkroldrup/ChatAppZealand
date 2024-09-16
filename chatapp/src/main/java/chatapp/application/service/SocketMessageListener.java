package chatapp.application.service;

import chatapp.domain.model.Message;

public interface  SocketMessageListener {
    void onMessageReceived(Message message);
}
