package nts.sixblack.chatservice.repository;

import nts.sixblack.chatservice.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findByChannelIdOrderByCreateDayDesc(long channelId, Pageable pageable);
}