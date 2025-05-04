package com.zap.wallet.token.service.encryption;

import com.zap.wallet.common.utils.constants.TokenTypeConstants;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class KeyReader {

    public static PrivateKey loadPrivateKey(String tokenType) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(ResourceUtils.getFile(getEncryptionKeyFile(tokenType, false)).getAbsolutePath()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(String tokenType) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(ResourceUtils.getFile(getEncryptionKeyFile(tokenType, true)).getAbsolutePath()));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private static String getEncryptionKeyFile(String tokenType, boolean isPublic) {
        String filePath = null;
        if(tokenType.equals(TokenTypeConstants.ACCESS_TOKEN) && !isPublic) {
            filePath = "classpath:keys/privateKey.txt";
        } else if(tokenType.equals(TokenTypeConstants.ACCESS_TOKEN) && !isPublic) {
            filePath = "classpath:keys/public_rsa.der";
        }
        return filePath;
    }

}
