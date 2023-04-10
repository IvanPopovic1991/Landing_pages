package Selenium_core;

/**
 * Instantiates concrete drivers. Deals with public abstract void createWebDriver() method
 * Instancira konkretne drivere; bavi se public abstract void createWebDriver() metodom
 */
public class DriverManagerFactory {
    /**
     * Method getDriverManager :
     * Name of the method - getDriverManager
     * DriverManager - return type (createWebDriver, GetWebDriver, quit methods from DriverManager class)
     *
     * @param type contains parameter type
     * @return
     */
    public static DriverManager getDriverManager(String type) {
        //since the DriverManager method has return type DriverManager we declare driverManager object
        DriverManager driverManager;
        switch (type) {
            case "CHROME":{
                /**
                 * driverManager = new ChromeDriverManager(); will be  the instance of class ChromeDriverManager
                 */
                driverManager = new ChromeDriverManager();
            }break;
            case "FIREFOX": {
                driverManager = new FirefoxDriverManager();
            }break;
            default: {
                driverManager = new ChromeDriverManager();
            }break;
        }
        return driverManager; //
    }
}
