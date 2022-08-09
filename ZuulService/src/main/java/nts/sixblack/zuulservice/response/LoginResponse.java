package nts.sixblack.zuulservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private long accountId;
    private String role;
    private String password;
    private String token;
}
