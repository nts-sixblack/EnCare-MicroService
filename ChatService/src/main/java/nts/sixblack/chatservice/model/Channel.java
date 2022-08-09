package nts.sixblack.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long channelId;
    private long accountDoctorId;
    private long accountUserId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDay;
}
