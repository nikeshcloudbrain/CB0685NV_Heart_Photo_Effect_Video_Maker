package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

public class DecryptEncryptPhoto {

    public static String DecryptStr(String str) {
        DecScriptPhoto script = new DecScriptPhoto();
        String newStr = null;
        try {
            newStr = new String(script.decrypt(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newStr;
    }

    public static String EncryptStr(String str1) {
        EncScriptPhoto script = new EncScriptPhoto();
        String newStr = null;
        try {
            newStr = EncScriptPhoto.bytesToHex(script.encrypt(str1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newStr;
    }
}
