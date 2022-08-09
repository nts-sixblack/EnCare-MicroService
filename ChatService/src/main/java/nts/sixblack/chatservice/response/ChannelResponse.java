package nts.sixblack.chatservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelResponse {
    private long channelId;
    private long accountDoctorId;
    private long accountUserId;
    private String name;
    private String image;
}
