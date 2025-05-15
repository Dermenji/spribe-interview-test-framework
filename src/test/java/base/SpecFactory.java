package base;

import config.FrameworkConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecFactory {
    private static final ThreadLocal<RequestSpecification> threadRequestSpec = ThreadLocal.withInitial(() ->
            new RequestSpecBuilder()
                    .setBaseUri(FrameworkConfig.getBaseUrl())
                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .setConfig(io.restassured.RestAssured.config()
                            .logConfig(LogConfig.logConfig()
                                    .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)))
                    .build()
    );

    private static final ThreadLocal<ResponseSpecification> threadResponseSpec = ThreadLocal.withInitial(() ->
            new ResponseSpecBuilder().build()
    );

    public static RequestSpecification getRequestSpec() {
        return threadRequestSpec.get();
    }

    public static ResponseSpecification getResponseSpec() {
        return threadResponseSpec.get();
    }
}
