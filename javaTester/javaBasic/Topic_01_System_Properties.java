package javaBasic;


public class Topic_01_System_Properties {
	static String osName=System.getProperty("os.name");

	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		// System.setProperty("webdriver.gecko.driver",projectPath + "/browserDrivers/geckodriver");
		
		System.out.println("OS: "+osName);
		//System.getProperties().list(System.out);

	}
	public static boolean isMac() {
        return (osName.indexOf("mac") >= 0);
    }

}
