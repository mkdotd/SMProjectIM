package UtilityFunctions;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.Console;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

public class IOFunction {
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES key size can be 128, 192, or 256 bits
        return keyGen.generateKey();
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static SecretKey loadKeyFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SecretKey) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void ValidateUserNamePassword(){
        Console console = System.console();
        String username = console.readLine("Username: ");

        while (!username.equals("madhan")){
            System.out.println("Invalid username. Please try again.");
            username = console.readLine("Username: ");
        }

        char[] password = console.readPassword("Password: ");

        SecretKey key = IOFunction.loadKeyFromFile("encKey.txt");
        try {

            while(!IOFunction.encrypt("1234", key).equals(IOFunction.encrypt(new String(password), key))){
                System.out.println("Invalid password. Please try again.");
                password = console.readPassword("Password: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Login successful.");
    }

}
