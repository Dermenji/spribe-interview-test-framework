package tests;

import base.BaseTest;
import builder.PlayerRequestBuilder;
import dto.request.CreatePlayerRequest;
import dto.request.UpdatePlayerRequest;
import dto.response.AllPlayersResponse;
import dto.response.PlayerCreateResponse;
import dto.response.PlayerItem;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.PlayerApiSteps;

@Epic("Player Management")
@Feature("Update Player API")
@Owner("alexandr.dermenji")
@Tag("api")
@Tag("player")
public class PlayerUpdateTest extends BaseTest {

    @Test(description = "Update player - positive")
    @Story("Update player by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create player, update their data using PATCH, and verify that changes are applied")
    public void updatePlayerSuccessfully() {
        CreatePlayerRequest createRequest = PlayerRequestBuilder.validPlayer(getFaker());
        PlayerCreateResponse createdPlayer = PlayerApiSteps.createPlayerViaGet(createRequest, "supervisor", 200);
        long playerId = createdPlayer.getId();

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .age(Integer.parseInt(createRequest.getAge()) + 1)
                .gender("female" .equalsIgnoreCase(createRequest.getGender()) ? "male" : "female")
                .login("updated_" + createRequest.getLogin())
                .password("newSecurePass123")
                .role("player")
                .screenName("updated_" + createRequest.getScreenName())
                .build();

        PlayerApiSteps.updatePlayer(updateRequest, "supervisor", playerId, 200);

        AllPlayersResponse allPlayers = PlayerApiSteps.getAllPlayers();
        PlayerItem updated = allPlayers.getPlayers().stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Updated player not found in list"));

        Allure.step("Verify updated player fields", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(updated.getAge(), updateRequest.getAge(), "Age mismatch");
            softAssert.assertEquals(updated.getGender(), updateRequest.getGender(), "Gender mismatch");
            softAssert.assertEquals(updated.getScreenName(), updateRequest.getScreenName(), "ScreenName mismatch");
            softAssert.assertAll();
        });
    }

    @Test(description = "Update player - negative (non-existent ID)")
    @Story("Update player by ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Attempt to update a player using a non-existent ID. Expect an error response from the API.")
    public void updatePlayerWithInvalidId() {
        long invalidId = Long.MAX_VALUE;

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .age(42)
                .gender("male")
                .login("nonexistent_user")
                .password("irrelevant123")
                .role("admin")
                .screenName("ghost_screen_name")
                .build();

        PlayerApiSteps.updatePlayer(updateRequest, "supervisor", invalidId, 404);
    }
}
