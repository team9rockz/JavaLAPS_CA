package sg.edu.nus.javalapsteam9.util;

import java.time.Instant;
import java.util.Date;

public final class Util {
	
	public static Date now() {
		return Date.from(Instant.now());
	}

}
