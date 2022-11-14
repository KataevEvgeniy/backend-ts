package taskScheduler.tokens;



import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import lombok.Getter;
import taskScheduler.User;

@Getter
public class AuthToken {
	private String token;
	
	public AuthToken(User user){
		this.token = DatatypeConverter.printHexBinary(AutoUpdatingKey.getKey().getEncoded()) + " " + user.getEmail();
	}
	
	public EncryptedAuthToken encryptToken() {
		byte[] cipheredText = new byte[0];
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, AutoUpdatingKey.getKey());
			cipheredText = cipher.doFinal(token.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		String encryptToken = DatatypeConverter.printHexBinary(cipheredText);
		return new EncryptedAuthToken(encryptToken);
	}
	
}
