/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terry
 */
@Serializable()
public class LoginMessage extends AbstractMessage {

    public String username;
    public String password;

    public LoginMessage() {
    }

    public LoginMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getMessage() {
        String hash = this.username + ":" + this.password;
        byte[] bytesOfMessage = hash.getBytes(Charset.forName("UTF-8"));

        MessageDigest md;
        byte[] thedigest;
        try {
            md = MessageDigest.getInstance("SHA");
            thedigest = md.digest(bytesOfMessage);
            Logger.getLogger(LoginMessage.class.getName()).log(Level.INFO, String.valueOf(md).trim());
            return String.valueOf(md).trim();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
