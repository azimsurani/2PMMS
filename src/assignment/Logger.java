package assignment;

public class Logger {
	
	private static boolean flag = false;
	
	public static void Log(String message) {
		if(flag)
			System.out.print(message);
	}

}
