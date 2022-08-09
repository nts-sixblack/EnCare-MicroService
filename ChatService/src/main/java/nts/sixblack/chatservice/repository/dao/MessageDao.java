package nts.sixblack.chatservice.repository.dao;

import nts.sixblack.chatservice.model.Message;
import nts.sixblack.chatservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDao {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> listMessageOfChannel(long channelId, Pageable pageable) {
        return messageRepository.findByChannelIdOrderByCreateDayDesc(channelId, pageable);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
