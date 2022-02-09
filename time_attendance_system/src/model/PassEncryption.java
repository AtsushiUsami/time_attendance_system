package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class PassEncryption {

    public String PassEncrypt(String originpass , String security){
        String result = "";

        if(originpass != null && !originpass.equals("")){
            byte[] bytes;
            String password = originpass + security;

            try {
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                result = DatatypeConverter.printHexBinary(bytes);
            } catch (NoSuchAlgorithmException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        return result;
    }


}
