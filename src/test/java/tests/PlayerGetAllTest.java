package tests;

import base.BaseTest;
import dto.response.AllPlayersResponse;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import steps.PlayerApiSteps;
import steps.PlayerAssertions;

@Epic("Player Management")
@Feature("Get All Players API")
@Owner("alexandr.dermenji")
@Tag("api")
@Tag("player")
public class PlayerGetAllTest extends BaseTest {

    @Test(description = "Get all players - no duplicates, not empty")
    @Story("Get all players")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that GET /player/get/all returns non-empty player list with unique IDs")
    public void getAllPlayersShouldReturnUniqueNonEmptyList() {
        AllPlayersResponse allPlayers = PlayerApiSteps.getAllPlayers();

        PlayerAssertions.verifyPlayerListIsValid(allPlayers);
    }

}
