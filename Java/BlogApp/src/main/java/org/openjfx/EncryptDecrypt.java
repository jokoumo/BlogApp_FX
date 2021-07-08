package org.openjfx;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class EncryptDecrypt {
//    private static Cipher cipher;
//    private static KeyGenerator keyGen;
//    private static SecretKey key;

    private static final StrongPasswordEncryptor enc = new StrongPasswordEncryptor();

    public static String encrypt(String pw) {
        return enc.encryptPassword(pw);
    }

    public static boolean checkPassword(String pw, String encPw) {
        return enc.checkPassword(pw, encPw);
    }

//    public EncryptionDecryption() throws Exception {
//        if(cipher == null) {
//            cipher = Cipher.getInstance("AES");
//            keyGen = KeyGenerator.getInstance("AES");
//            keyGen.init(128);
//            key = keyGen.generateKey();
//        }
//    }
//
//    public String encrypt(String pw) throws Exception {
//        byte[] pwByte = pw.getBytes();
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encryptedByte = cipher.doFinal(pwByte);
//        Base64.Encoder encoder = Base64.getEncoder();
//        pw = encoder.encodeToString(encryptedByte);
//        return pw;
//    }
//
//    public String decrypt(String pw) throws Exception {
//        Base64.Decoder decoder = Base64.getDecoder();
//        byte[] pwByte = decoder.decode(pw);
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decryptedByte = cipher.doFinal(pwByte);
//        pw = new String(decryptedByte);
//        return pw;
//    }
}
