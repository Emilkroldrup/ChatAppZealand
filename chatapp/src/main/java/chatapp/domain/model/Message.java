package chatapp.domain.model;

public class Message {
    private String content;
    private String sender;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }
}

