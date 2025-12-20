package de.id.swaglabs.core;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightArtifactsExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {
    private static final Path TRACE_DIR = Paths.get("artifacts", "traces");
    private static final Path SCREENSHOT_DIR = Paths.get("artifacts", "screenshots");

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
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
        String safeName = safeFileName(context);

        Page page = BaseTest.getPage();
        if (page != null) {
            try {
                page.screenshot(new Page.ScreenshotOptions()
                        .setFullPage(true)
                        .setPath(SCREENSHOT_DIR.resolve(safeName + ".png")));
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        BrowserContext browserContext = BaseTest.getContext();
        if (browserContext == null) return;

        boolean failed = context.getExecutionException().isPresent();
        if (failed) {
            String safeName = safeFileName(context);
            browserContext.tracing().stop(new Tracing.StopOptions()
                    .setPath(TRACE_DIR.resolve(safeName + ".zip")));
        } else {
            browserContext.tracing().stop();
        }
    }

    private String safeFileName(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();
        return (className + "__" + methodName).replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
