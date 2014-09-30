package Test;

import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.Rule;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentParameterized.class)
public class AmpSauceTest implements SauceOnDemandSessionIdProvider {

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("shesh11", "2c22657c-373b-4af6-b8e6-813f84523a5a");
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
    private String browser;
    private String os;
    private String version;
    private String sessionId;
    private WebDriver driver;
    
    public AmpSauceTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"Windows 8.1", "11", "internet explorer"});
        browsers.add(new String[]{"OSX 10.8", "6", "safari"});
        return browsers;
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", "Sauce Sample Test");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test verifying the title of the www.admarketplace.com
     * @throws Exception
     */
    @Test
    public void amp() throws Exception {
    	driver.get("http://www.admarketplace.com");
		Thread.sleep(2000);
	    assertEquals("Amp website is not loaded","http://www.admarketplace.com" ,driver.getCurrentUrl());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
