package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createNewMessage(Message message) {
        if (message.getMessage_text().length() > 0 &&
            message.getMessage_text().length() < 255)
                return messageDAO.createNewMessage(message);
        return null;
    }

    public List<Message> retrieveAllMessages() {
        return messageDAO.retrieveAllMessages();
    }

    public Message retrieveMessageById(int id) {
        return messageDAO.retrieveMessageById(id);
    }

    public Message deleteMessageById(int id) {
        Message deletedMessage = messageDAO.searchById(id);
        messageDAO.deleteMessageById(id);
        return deletedMessage;
    }

    public Message updateMessageById(int id, String newMessage) {
        if (newMessage.length() > 0 && newMessage.length() < 255)
            return messageDAO.updateMessageById(id, newMessage);
        return null;
    }

    public List<Message> retrieveAllMessagesById(int id) {
        return messageDAO.retrieveAllMessagesById(id);
    }
}