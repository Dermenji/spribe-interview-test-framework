
package tests;

import base.BaseTest;
import builder.PlayerRequestBuilder;
import dto.request.CreatePlayerRequest;
import dto.request.PlayerIdRequest;
import dto.response.PlayerCreateResponse;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.PlayerApiSteps;

@Epic("Player Management")
@Feature("Get Player By ID API")
@Owner("alexandr.dermenji")
@Tag("api")
@Tag("player")
public class PlayerGetByIdTest extends BaseTest {

    @Test(description = "Get player by ID - positive (POST with body)")
    @Story("Get player by ID using POST")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create player and verify retrieval via POST /player/get with playerId in request body")
    public void getPlayerByIdViaPost() {
        CreatePlayerRequest request = PlayerRequestBuilder.validPlayer(getFaker());

        PlayerCreateResponse createdPlayer = PlayerApiSteps.createPlayerViaGet(request, "supervisor", 200);

        PlayerIdRequest getRequest = new PlayerIdRequest(createdPlayer.getId());

        PlayerCreateResponse fetchedPlayer = PlayerApiSteps.getPlayerByPost(getRequest, 200);

        Allure.step("Validate fetched player matches created player", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(fetchedPlayer.getId(), createdPlayer.getId(), "ID mismatch");
            softAssert.assertEquals(fetchedPlayer.getLogin(), createdPlayer.getLogin(), "Login mismatch");
            softAssert.assertAll();
        });
    }

    @Test(description = "Get player by invalid ID (Long.MAX_VALUE)")
    @Story("Get player by ID using POST")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that API returns error when trying to get player with non-existent ID (Long.MAX_VALUE)")
    public void getPlayerByInvalidId() {
        PlayerIdRequest request = new PlayerIdRequest(Long.MAX_VALUE);

        PlayerApiSteps.getPlayerByPost(request, 404);
    }
}
