package chatapp.domain.model;

public class Message {
    private String content;
    private String sender;
    private String targetClientName;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public Message(String content, String sender, String targetClientName) {
        this.content = content;
        this.sender = sender;
        this.targetClientName = targetClientName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setTargetClientName(String targetClientName) {
        this.targetClientName = targetClientName;
    }

    public String getTargetClientName() {
        return targetClientName;
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }
}

