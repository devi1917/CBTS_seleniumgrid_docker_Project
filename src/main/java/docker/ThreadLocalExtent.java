package docker;


import com.relevantcodes.extentreports.ExtentTest;

public class ThreadLocalExtent {
    private static ThreadLocal<ExtentTest> threadExtent = new ThreadLocal();

    public ThreadLocalExtent(ExtentTest extentTest) {
        extentTest = threadExtent.get();
    }

    public static synchronized void setExtent(ExtentTest extentTest) {
        threadExtent.set(extentTest);
    }

    public static synchronized ExtentTest getExtent() {
        return threadExtent.get();
    }

    public static void endReport() {
        threadExtent.remove();
    }

}