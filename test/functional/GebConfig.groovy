import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.firefox.FirefoxDriver

/**
 * Necesario para hacer funcionar los test funcionales.
 */
//driver = { new ChromeDriver()}
System.setProperty("webdriver.chrome.driver", "/usr/local/share/chromedriver");

environments {

    // run as “grails -Dgeb.env=chrome test-app”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome {
        driver = { new ChromeDriver() }
    }

//    firefox {
//        driver = { new FirefoxDriver() }
//    }
}