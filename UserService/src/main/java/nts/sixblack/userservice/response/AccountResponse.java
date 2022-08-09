package nts.sixblack.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private long accountId;
    private String phone;
    private String password;
    private String role;
    private String name;
    private String avatar;
    private String description;
    private String birthday;
    private String createDate;
    private String updateDate;
}
