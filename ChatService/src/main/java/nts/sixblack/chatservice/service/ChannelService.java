package nts.sixblack.chatservice.service;

import nts.sixblack.chatservice.model.Channel;
import nts.sixblack.chatservice.repository.dao.ChannelDao;
import nts.sixblack.chatservice.response.ChannelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    public List<ChannelResponse> listChannel(long accountId, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Channel> channelList = channelDao.listChannelOfUser(accountId, pageable);

        List<ChannelResponse> channelResponseList = new ArrayList<ChannelResponse>();
        for (Channel channel:channelList) {
            ChannelResponse channelResponse = transform(channel);
            channelResponseList.add(channelResponse);
        }
        return channelResponseList;
    }

    private ChannelResponse transform(Channel channel) {
        ChannelResponse channelResponse = new ChannelResponse();
        channelResponse.setChannelId(channel.getChannelId());
        channelResponse.setAccountDoctorId(channel.getAccountDoctorId());
        channelResponse.setAccountUserId(channel.getAccountUserId());

        return channelResponse;
    }

    public boolean newChannel(long accountDoctorId, long accountUserId) {
        if (existChannel(accountDoctorId, accountUserId)) {
            return false;
        }
        Channel channel = new Channel();
        channel.setAccountDoctorId(accountDoctorId);
        channel.setAccountUserId(accountUserId);
        channel.setCreateDay(new Date());
        return channelDao.save(channel) != null;
    }

    private boolean existChannel(long accountDoctorId, long accountUserId) {
        return channelDao.countByAccountDoctorIdAndAccountUserId(accountDoctorId, accountUserId) > 0;
    }

    public Channel findByChannelId(long channelId) {
        return channelDao.findById(channelId);
    }
}
