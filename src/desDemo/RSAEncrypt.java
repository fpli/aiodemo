package desDemo;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

/**
 * RSA(RSA加密算法是非对称加密算法) 加密与解密
 * 使用RSA加密算法需要使用公钥和私钥, 公钥公开给他人，私钥自己保管。(公钥与私钥组成秘钥对)
 * 业务场景:
 * 1 使用公钥加密，使用私钥解码。 a 给 b 发信 先使用b的公钥对内容进行加密，再使用a自己的私钥签名，最后发送
 * 2 使用私钥签名，使用公钥验签。 b 收到信件时 先用a的公钥验签，再使用b自己私钥解密，最后得到信息内容
 */
public class RSAEncrypt {

    private static final Map<Integer, String> keyMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        genKeyPair();
        String message = "测试RSA非对称加密";
        String messageEn = encrypt(message, keyMap.get(0));
        String messageDe = decrypt(messageEn, keyMap.get(1));
        System.out.println(messageDe);
    }

    /**
     * 功能: 生成秘钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey  publicKey  = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString  = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString((privateKey.getEncoded()));
        keyMap.put(0, publicKeyString);
        keyMap.put(1, privateKeyString);
    }

    /**
     * 功能: 公钥加密
     *
     * @param original
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String original, String publicKey) throws Exception {
        byte[] encodedKey = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] bytes = cipher.doFinal(original.getBytes("UTF-8"));
        String outStr = Base64.getEncoder().encodeToString(bytes);
        return outStr;
    }

    /**
     * 描述: 私钥解密
     *
     * @param ciphertext
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String ciphertext, String privateKey) throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(ciphertext.getBytes("UTF-8"));
        byte[] encodedKey = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] bytes = cipher.doFinal(inputByte);
        String outStr = new String(bytes);
        return outStr;
    }
}
