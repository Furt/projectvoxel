package com.cubes.network;

import java.io.IOException;

public interface BitSerializable {

    /**
     *
     * @param outputStream
     */
    public abstract void write(BitOutputStream outputStream);

    /**
     *
     * @param inputStream
     * @throws IOException
     */
    public abstract void read(BitInputStream inputStream) throws IOException;
}
