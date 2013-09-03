package com.cubes.network;

import java.io.IOException;

public interface BitSerializable {

    public abstract void write(BitOutputStream outputStream);

    public abstract void read(BitInputStream inputStream) throws IOException;
}
