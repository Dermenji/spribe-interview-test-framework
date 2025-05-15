package tests;

import base.BaseTest;
import builder.PlayerRequestBuilder;
import dto.request.CreatePlayerRequest;
import dto.request.PlayerIdRequest;
import dto.response.AllPlayersResponse;
import dto.response.PlayerCreateResponse;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.PlayerApiSteps;

@Epic("Player Management")
@Feature("Delete Player API")
@Owner("alexandr.dermenji")
@Tag("api")
@Tag("player")
public class PlayerDeleteTest extends BaseTest {

    @Test(description = "Delete player - positive")
    @Story("Delete player by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create player and verify successful deletion using DELETE /player/delete/{editor}")
    public void deletePlayerById() {
        CreatePlayerRequest createRequest = PlayerRequestBuilder.validPlayer(getFaker());
        PlayerCreateResponse createdPlayer = PlayerApiSteps.createPlayerViaGet(createRequest, "supervisor", 200);
        long deletedId = createdPlayer.getId();

        PlayerIdRequest deleteRequest = new PlayerIdRequest(deletedId);
        PlayerApiSteps.deletePlayer(deleteRequest, "supervisor", 204);

        AllPlayersResponse allPlayers = PlayerApiSteps.getAllPlayers();

        Allure.step("Validate deleted player ID is not present in list", () -> {
            boolean found = allPlayers.getPlayers().stream()
                    .anyMatch(p -> p.getId().equals(deletedId));

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(found, "Deleted player ID [" + deletedId + "] is still present in the list!");
            softAssert.assertAll();
        });
    }

}
