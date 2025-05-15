
package dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayerRequest {
    private String age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
