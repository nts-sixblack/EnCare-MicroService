package nts.sixblack.chatservice.repository.dao;

import nts.sixblack.chatservice.model.*;
import nts.sixblack.chatservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChannelDao {

    @Autowired
    private ChannelRepository channelRepository;

    public List<Channel> listChannelOfUser(long accountId, Pageable pageable) {
        return channelRepository.findByAccountId(accountId, pageable);
    }

    public int countByAccountDoctorIdAndAccountUserId(long accountDoctorId, long accountUserId) {
        return channelRepository.countByAccountDoctorIdAndAccountUserId(accountDoctorId, accountUserId);
    }

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    public Channel findById(long channelId) {
        return channelRepository.findByChannelId(channelId);
    }
}
