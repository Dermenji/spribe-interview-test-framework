
package base;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    private static final ThreadLocal<Faker> threadFaker = ThreadLocal.withInitial(Faker::new);

    protected Faker getFaker() {
        return threadFaker.get();
    }

    @BeforeClass
    public void setUp() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
        // Hook for future global setup per test class (e.g. auth, logging, test data)
    }

}
