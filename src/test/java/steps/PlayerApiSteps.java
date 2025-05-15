package steps;

import base.SpecFactory;
import dto.request.CreatePlayerRequest;
import dto.request.PlayerIdRequest;
import dto.request.UpdatePlayerRequest;
import dto.response.AllPlayersResponse;
import dto.response.PlayerCreateResponse;
import endpoints.PlayerEndpoint;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class PlayerApiSteps {

    @Step("Create player using GET workaround (editor = {editor} (expected status = {expectedStatus}))")
    public static PlayerCreateResponse createPlayerViaGet(CreatePlayerRequest request, String editor, int expectedStatus) {
        return given()
                .spec(SpecFactory.getRequestSpec())
                .pathParam("editor", editor)
                .queryParam("age", request.getAge())
                .queryParam("gender", request.getGender())
                .queryParam("login", request.getLogin())
                .queryParam("password", request.getPassword())
                .queryParam("role", request.getRole())
                .queryParam("screenName", request.getScreenName())
                .when()
                .get(PlayerEndpoint.CREATE_PLAYER.getPath())
                .then()
                .spec(SpecFactory.getResponseSpec())
                .statusCode(expectedStatus)
                .extract().as(PlayerCreateResponse.class);
    }

    @Step("Get player by ID using POST (id = {request.playerId} (expected status = {expectedStatus}))")
    public static PlayerCreateResponse getPlayerByPost(PlayerIdRequest request, int expectedStatus) {
        return given()
                .spec(SpecFactory.getRequestSpec())
                .body(request)
                .when()
                .post(PlayerEndpoint.GET_PLAYER.getPath())
                .then()
                .spec(SpecFactory.getResponseSpec())
                .statusCode(expectedStatus)
                .extract().as(PlayerCreateResponse.class);
    }

    @Step("Delete player by ID = {request.playerId} (editor = {editor} (expected status = {expectedStatus}))")
    public static void deletePlayer(PlayerIdRequest request, String editor, int expectedStatus) {
        given()
                .spec(SpecFactory.getRequestSpec())
                .pathParam("editor", editor)
                .body(request)
                .accept("*/*")
                .when()
                .delete(PlayerEndpoint.DELETE_PLAYER.getPath())
                .then()
                .spec(SpecFactory.getResponseSpec())
                .statusCode(expectedStatus);
    }

    @Step("Get all players")
    public static AllPlayersResponse getAllPlayers() {
        return given()
                .spec(SpecFactory.getRequestSpec())
                .when()
                .get(PlayerEndpoint.GET_ALL_PLAYERS.getPath())
                .then()
                .statusCode(200)
                .extract().as(AllPlayersResponse.class);
    }

    @Step("Update player ID = {id} by editor = {editor} (expected status = {expectedStatus})")
    public static void updatePlayer(UpdatePlayerRequest request, String editor, long id, int expectedStatus) {
        given()
                .spec(SpecFactory.getRequestSpec())
                .pathParam("editor", editor)
                .pathParam("id", id)
                .body(request)
                .accept("*/*")
                .when()
                .patch(PlayerEndpoint.UPDATE_PLAYER.getPath())
                .then()
                .statusCode(expectedStatus);
    }

}
