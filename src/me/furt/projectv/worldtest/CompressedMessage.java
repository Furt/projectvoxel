package me.furt.projectv.worldtest;

import com.jme3.network.serializing.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * ProjectV
 *
 * @author Furt
 */
public class CompressedMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private final byte[] payload;

    public CompressedMessage(Serializable ob) throws IOException {
        if (ob == null) {
            payload = null;
        } else {
            ByteArrayOutputStream baos = null;
            OutputStream os = null;
            ObjectOutputStream oos = null;

            try {
                baos = new ByteArrayOutputStream();
                os = new DeflaterOutputStream(baos);
                oos = new ObjectOutputStream(os);
                oos.writeObject(ob);
                oos.close();
                payload = baos.toByteArray();
            } finally {
                if (baos != null) {
                    baos.close();
                }
                if (os != null) {
                    os.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }
        }
    }

    public Serializable getPayload() throws IOException, ClassNotFoundException {
        if (payload == null) {
            return null;
        } else {
            ByteArrayInputStream bais = null;
            InputStream is = null;
            ObjectInputStream ois = null;

            try {
                bais = new ByteArrayInputStream(payload);
                is = new InflaterInputStream(bais);
                ois = new ObjectInputStream(is);
                return (Serializable) ois.readObject();

            } finally {
                if (ois != null) {
                    ois.close();
                }
                if (is != null) {
                    is.close();
                }
                if (bais != null) {
                    bais.close();
                }
            }
        }
    }

    public int getPayloadSize() {
        return payload.length;
    }

    public Class serializer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public short id() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
