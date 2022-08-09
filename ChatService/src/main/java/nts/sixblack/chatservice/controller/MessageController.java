package nts.sixblack.chatservice.controller;

import nts.sixblack.chatservice.form.*;
import nts.sixblack.chatservice.model.*;
import nts.sixblack.chatservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(@RequestBody MessageForm messageForm) {
        Message message = messageService.newMessage(messageForm);

        Channel channel = channelService.findByChannelId(messageForm.getChannelId());
        template.convertAndSend("/topic/messages/"+channel.getAccountDoctorId(), message);
        template.convertAndSend("/topic/messages/"+channel.getAccountUserId(), message);
    }
}
