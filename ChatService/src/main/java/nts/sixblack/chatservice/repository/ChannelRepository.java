package nts.sixblack.chatservice.repository;

import nts.sixblack.chatservice.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("select c from Channel c where c.accountDoctorId = ?1 or c.accountUserId = ?1")
    public List<Channel> findByAccountId(long accountId, Pageable pageable);

    public int countByAccountDoctorIdAndAccountUserId(long accountDoctorId, long accountUserId);

    public Channel findByChannelId(long channelId);
}