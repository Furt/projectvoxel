/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.jme3.network.serializing.Serializer;
import me.furt.projectv.network.messages.LoginMessage;

/**
 *
 * @author Terry
 */
class Util {

    static void registerSerializers() {
       Serializer.registerClass(LoginMessage.class);
    }
    
}
