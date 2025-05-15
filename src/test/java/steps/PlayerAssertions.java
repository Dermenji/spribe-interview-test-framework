package steps;

import dto.response.AllPlayersResponse;
import dto.response.PlayerItem;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerAssertions {
    @Step("Verify player list is not empty and all IDs are unique")
    public static void verifyPlayerListIsValid(AllPlayersResponse allPlayers) {
        List<Long> ids = allPlayers.getPlayers().stream()
                .map(PlayerItem::getId)
                .collect(Collectors.toList());

        Set<Long> uniqueIds = new HashSet<>(ids);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(ids.isEmpty(), "Player list is empty!");
        softAssert.assertEquals(uniqueIds.size(), ids.size(), "Duplicate player IDs found!");
        softAssert.assertAll();
    }
}
