package org.unique.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * //TODO 序列化类
 * @author rex
 */
public class SerializeUtil {

    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Serializable object) {
    	byte[] bytes = null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    bytes = baos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bytes;
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Serializable unserialize(byte[] bytes) {
    	Serializable obj = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            obj = (Serializable) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return obj;
    }
    
}