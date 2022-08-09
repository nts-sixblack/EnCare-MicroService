package nts.sixblack.chatservice.service;

import nts.sixblack.chatservice.form.*;
import nts.sixblack.chatservice.model.*;
import nts.sixblack.chatservice.repository.dao.MessageDao;
import nts.sixblack.chatservice.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public List<MessageResponse> listMessageOfChannel(long channelId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Message> messageList = messageDao.listMessageOfChannel(channelId, pageable);
        List<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
        for (Message message:messageList) {
            MessageResponse messageResponse = transform(message);
            messageResponseList.add(messageResponse);
        }
        return messageResponseList;
    }

    private MessageResponse transform(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageId(message.getMessageId());
        messageResponse.setChannelId(message.getChannelId());
        messageResponse.setAccountId(message.getAccountId());
        messageResponse.setMessage(message.getMessage());


        messageResponse.setCreateDay(message.getCreateDay());
        return messageResponse;
    }

    public Message newMessage(MessageForm messageForm) {
        Message message = new Message();
        message.setChannelId(messageForm.getChannelId());
        message.setAccountId(messageForm.getAccountId());
        message.setMessage(messageForm.getMessage());
        message.setCreateDay(new Date());
        return messageDao.save(message);
    }
}
