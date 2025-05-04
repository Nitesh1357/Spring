package com.zap.wallet.token.service.encryption;

import com.zap.wallet.common.utils.FileUtils;
import lombok.CustomLog;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.File;
import java.security.*;
import java.util.Base64;

public class RSAEncryptionUtil {


    public static String encrypt(String valueToEncrypt, String tokenType) throws Exception {
        PrivateKey privateKey = KeyReader.loadPrivateKey(tokenType);
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedMessage = encryptionCipher.doFinal(valueToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public static String decrypt(String valueToDecrypt, String tokenType) throws Exception {
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        PublicKey publicKey = KeyReader.loadPublicKey(tokenType);
        decryptionCipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessage = decryptionCipher.doFinal(valueToDecrypt.getBytes());
        String decryption = new String(decryptedMessage);
        return new String(decryptedMessage);
    }

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom();
            keyPairGenerator.initialize(2048, secureRandom);
            KeyPair pair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = pair.getPublic();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("public key = "+ publicKeyString);
            String val = "G:\\Personal\\FreeLancing\\Zap Payment\\Source Code\\backend\\API-GATEWAY\\src\\main\\resources\\keys\\";
            File file = new File(val + "privateKey.txt");
            FileUtils.writeToFile(file, publicKeyString);
            PrivateKey privateKey = pair.getPrivate();
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            System.out.println("private key = "+ privateKeyString);
            file = new File(val + "publicKey.txt");
            FileUtils.writeToFile(file, privateKeyString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
