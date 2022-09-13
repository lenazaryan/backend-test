package api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserConnectResult {
    private String status;
    private String username;
    private String spoonacularPassword;
    private String hash;
}
