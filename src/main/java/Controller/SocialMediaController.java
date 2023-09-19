package Controller;

import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newUserHandler);
        app.post("/login", this::processLoginHandler);

        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::retrieveAllMsgHandler);
        app.get("/messages/{message_id}", this::retrieveMsgIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::allUserMsgHandler);

        return app;
    }

    private void newUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.processNewUser(account);
        if (newAccount != null) {
            ctx.json(newAccount);
        }
        else
            ctx.status(400);
    }
    private void processLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.processUserLogin(account);
        //loginAccount.setAccount_id(loginAccount.getAccount_id());
        if (loginAccount != null)
            ctx.json(loginAccount);
        else
            ctx.status(401);
    }


    private void newMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createNewMessage(message);

        if (newMessage != null) {
            ctx.json(newMessage);
        }
        else   
            ctx.status(400);
    }
    private void retrieveAllMsgHandler(Context ctx) {
        List<Message> messages = messageService.retrieveAllMessages();
        ctx.json(messages);
    }
    private void retrieveMsgIdHandler(Context ctx) {
        Message message = messageService.retrieveMessageById(Integer.valueOf(ctx.pathParam("message_id")));
        if (message != null)
            ctx.json(message);
    }
    private void deleteMessageHandler(Context ctx) {
        int id = Integer.valueOf(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if (message != null) {
            ctx.json(message);
        }
        
    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper om = new ObjectMapper();
        Message mes1 = om.readValue(ctx.body(), Message.class);
        String newMessage = mes1.getMessage_text();
        Message mes2 = messageService.updateMessageById(id, newMessage);
        if (mes2 != null)
            ctx.json(mes2);
        else
            ctx.status(400);
    }
    private void allUserMsgHandler(Context ctx) {
        List<Message> messages = messageService.retrieveAllMessagesById(Integer.valueOf(ctx.pathParam("account_id")));
        ctx.json(messages);
    }


}