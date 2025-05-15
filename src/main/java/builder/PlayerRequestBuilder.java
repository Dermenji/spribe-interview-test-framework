
package builder;

import com.github.javafaker.Faker;
import dto.request.CreatePlayerRequest;

import java.util.UUID;

public class PlayerRequestBuilder {

    public static CreatePlayerRequest validPlayer(Faker faker) {
        return CreatePlayerRequest.builder()
                .age(String.valueOf(faker.number().numberBetween(18, 60)))
                .gender(faker.options().option("male", "female"))
                .login(faker.internet().domainName())
                .password(faker.internet().password(8, 16))
                .role(faker.options().option("admin"))
                .screenName(faker.name().username())
                .build();
    }

    public static CreatePlayerRequest notCorrectAgePlayer(Faker faker) {
        CreatePlayerRequest request = validPlayer(faker);
        request.setAge("abc");
        return request;
    }
}
