package nts.sixblack.chatservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private long messageId;
    private long channelId;
    private long accountId;
    private String name;
    private String image;
    private String message;
    private Date createDay;
}
