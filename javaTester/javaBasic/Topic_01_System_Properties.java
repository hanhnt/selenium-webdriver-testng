package javaBasic;


public class Topic_01_System_Properties {

	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		System.out.print(projectPath);
		// System.setProperty("webdriver.gecko.driver",projectPath + "/browserDrivers/geckodriver");

	}

}
