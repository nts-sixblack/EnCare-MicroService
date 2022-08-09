package nts.sixblack.chatservice.check;

import nts.sixblack.chatservice.model.*;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class ClientSessionHandler implements StompSessionHandler {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("connect "+session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println(exception);
        System.out.println(session.getSessionId());
        System.out.println(command.getMessageType());
        System.out.println(headers);
        System.out.println(payload);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println(exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message messageResponse = (Message) payload;
        System.out.println("messageId: "+messageResponse.getMessageId());
        System.out.println("channel: "+messageResponse.getChannelId());
        System.out.println("account: "+messageResponse.getAccountId());
        System.out.println("message: "+messageResponse.getMessage());
        System.out.println("day: "+messageResponse.getCreateDay());

    }
}
