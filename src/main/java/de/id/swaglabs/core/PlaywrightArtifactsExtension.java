package de.id.swaglabs.core;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.extension.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightArtifactsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, TestWatcher {
    private static final Path TRACE_DIR = Paths.get("artifacts", "traces");
    private static final Path SCREENSHOT_DIR = Paths.get("artifacts", "screenshots");

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Files.createDirectories(TRACE_DIR);
        Files.createDirectories(SCREENSHOT_DIR);

        BrowserContext browserContext = BaseTest.getContext();
        if (browserContext != null) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Page page = BaseTest.getPage();
        if (page == null) return;

        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true)
                    .setPath(SCREENSHOT_DIR.resolve(safeFileName(context) + ".png")));
        } catch (Exception ignored) {
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        BrowserContext browserContext = BaseTest.getContext();
        if (browserContext == null) return;

        boolean failed = context.getExecutionException().isPresent();
        try {
            if (failed) {
                browserContext.tracing().stop(new Tracing.StopOptions()
                        .setPath(TRACE_DIR.resolve(safeFileName(context) + ".zip")));
            } else {
                browserContext.tracing().stop(); // discard
            }
        } catch (Exception ignored) {
        }
    }

    private String safeFileName(ExtensionContext context) {
        return (context.getRequiredTestClass().getSimpleName() + "__" +
                context.getRequiredTestMethod().getName())
                .replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
