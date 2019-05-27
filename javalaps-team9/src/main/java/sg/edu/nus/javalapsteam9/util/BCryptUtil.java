package sg.edu.nus.javalapsteam9.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptUtil {
	
	private static final int ROUNDS = 8;
	
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(ROUNDS));
	}
	
	public static boolean isValidPassword(String password, String hashPassword) {
		return BCrypt.checkpw(password, hashPassword);
	}

}
