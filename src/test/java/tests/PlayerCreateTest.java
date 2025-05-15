
package tests;

import base.BaseTest;
import builder.PlayerRequestBuilder;
import dto.request.CreatePlayerRequest;
import dto.response.PlayerCreateResponse;

import static base.SpecFactory.getRequestSpec;
import static base.SpecFactory.getResponseSpec;
import static io.restassured.RestAssured.*;

import endpoints.PlayerEndpoint;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.PlayerApiSteps;

/**
 * ⚠️ WARNING: According to the test assignment documentation, player creation must be done via POST with a JSON body.
 * However, the provided Swagger documentation incorrectly specifies a GET endpoint with query parameters.
 * <p>
 * This is a critical issue (fatal bug) because:
 * - GET requests should be idempotent and must not alter server state.
 * - Creating resources via GET violates REST principles and may introduce security and caching issues.
 * <p>
 * This test implements the GET version as specified in Swagger for completeness and validation purposes,
 * but the POST-based implementation (createPlayerPositive) should be considered the correct one.
 */
@Epic("Player Management")
@Feature("Player Creation API")
@Owner("alexandr.dermenji")
@Tag("api")
@Tag("player")
public class PlayerCreateTest extends BaseTest {

    //This test case is not working by design, but is example that Player creation should be with POST
    @Test(description = "Create player - positive (POST with body)")
    @Story("Create player using POST")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate that player can be successfully created using POST endpoint with full body")
    public void createPlayerPositive() {
        CreatePlayerRequest request = PlayerRequestBuilder.validPlayer(getFaker());

        PlayerCreateResponse response = given()
                .spec(getRequestSpec())
                .pathParam("editor", "supervisor")
                .body(request)
                .when()
                .post(PlayerEndpoint.CREATE_PLAYER.getPath())
                .then()
                .spec(getResponseSpec())
                .statusCode(200)
                .extract()
                .as(PlayerCreateResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getLogin(), request.getLogin(), "Login mismatch");
        softAssert.assertEquals(response.getAge(), request.getAge(), "Age mismatch");
        softAssert.assertEquals(response.getGender(), request.getGender(), "Gender mismatch");
        softAssert.assertEquals(response.getPassword(), request.getPassword(), "Password mismatch");
        softAssert.assertEquals(response.getRole(), request.getRole(), "Role mismatch");
        softAssert.assertEquals(response.getScreenName(), request.getScreenName(), "ScreenName mismatch");
        softAssert.assertTrue(response.getId() >= 0, "ID should be non-negative");
        softAssert.assertAll();
    }

    @Test(description = "Create player - positive (GET with params)")
    @Story("Swagger mismatch validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify player creation via GET as per incorrect Swagger, although POST is expected")
    public void createPlayer_ShouldReturnValidPlayerData() {
        CreatePlayerRequest request = PlayerRequestBuilder.validPlayer(getFaker());

        PlayerCreateResponse createdPlayer = PlayerApiSteps.createPlayerViaGet(request, "supervisor", 200);

        Allure.step("Validate created player", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(createdPlayer.getLogin(), request.getLogin(), "Login mismatch");
            softAssert.assertEquals(createdPlayer.getAge(), request.getAge(), "Age mismatch");
            softAssert.assertEquals(createdPlayer.getGender(), request.getGender(), "Gender mismatch");
            softAssert.assertEquals(createdPlayer.getPassword(), request.getPassword(), "Password mismatch");
            softAssert.assertEquals(createdPlayer.getRole(), request.getRole(), "Role mismatch");
            softAssert.assertEquals(createdPlayer.getScreenName(), request.getScreenName(), "ScreenName mismatch");
            softAssert.assertTrue(createdPlayer.getId() >= 0, "ID should be non-negative");
            softAssert.assertAll();
        });
    }
}
