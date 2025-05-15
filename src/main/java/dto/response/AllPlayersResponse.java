
package dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AllPlayersResponse {
    private List<PlayerItem> players;
}
