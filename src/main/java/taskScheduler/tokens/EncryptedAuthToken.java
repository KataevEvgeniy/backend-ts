package taskScheduler.tokens;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EncryptedAuthToken {
	private String encryptedStringToken;
	
	public boolean isTrue() throws BadPaddingException{
		byte[] uncipheredText = new byte[0];
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, AutoUpdatingKey.getKey());
			uncipheredText = cipher.doFinal(DatatypeConverter.parseHexBinary(this.encryptedStringToken));
			if((new String(uncipheredText,"UTF-8")).split(" ")[0].equals(DatatypeConverter.printHexBinary(AutoUpdatingKey.getKey().getEncoded()))){
				return true;
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
