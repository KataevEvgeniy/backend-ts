package taskScheduler.tokens;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.KeyGenerator;

public class AutoUpdatingKey {
	static private Key key;
	static private Calendar updateDate = new GregorianCalendar();
	
	static {
		updateDate = new GregorianCalendar(
				updateDate.get(Calendar.YEAR),
				updateDate.get(Calendar.MONTH),
				updateDate.get(Calendar.DAY_OF_MONTH));
		updateDate.add(Calendar.DAY_OF_YEAR, 1);
		
		
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(256);
			
		    key = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Key getKey() {
		Calendar temp = (GregorianCalendar)updateDate.clone();
		temp.add(Calendar.MINUTE, 1);

		if (temp.after(new GregorianCalendar())) {//Check if the updateDate is up to date 
			return key;
		}
		updateDate.add(Calendar.DAY_OF_YEAR, 1);
		
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(256);
		    key = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
	
}
