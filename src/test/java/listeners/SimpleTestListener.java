package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class SimpleTestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed!");
    }
}
