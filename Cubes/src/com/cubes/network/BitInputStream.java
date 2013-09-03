package com.cubes.network;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream
{
  private InputStream in;
  private int lastByte;
  private int bits = 0;

  public BitInputStream(InputStream in)
  {
    this.in = in;
  }

  public <T extends Enum<T>> T readEnum(Class<T> enumClass)
    throws IOException
  {
    Enum[] enumConstants = (Enum[])enumClass.getEnumConstants();
    int bitsCount = BitUtil.getNeededBitsCount(enumConstants.length);
    return (T) enumConstants[readBits(bitsCount)];
  }

  public String readString_UTF8(int maximumBytesCountBits) throws IOException {
    int bytesCount = readBits(maximumBytesCountBits);
    byte[] bytes = readBytes(bytesCount);
    return new String(bytes, "UTF-8");
  }

  public byte[] readBytes(int bytesCount) throws IOException {
    byte[] bytes = new byte[bytesCount];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte)readBits(8);
    }
    return bytes;
  }

  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readBits(32));
  }

  public int readInteger() throws IOException {
    return readBits(32);
  }

  public boolean readBoolean() throws IOException {
    return readBits(1) == 1;
  }

  public int readBits(int count) throws IOException {
    if (count == 0) {
      throw new IllegalArgumentException("Cannot read 0 bits.");
    }
    if (count > 32) {
      throw new IllegalArgumentException("Bit count overflow: " + count);
    }

    int result = 0;

    int remainingCount = count;
    while (remainingCount > 0)
    {
      if (this.bits == 0) {
        int b = this.in.read();
        if (b < 0) {
          throw new IOException("End of stream reached.");
        }
        this.lastByte = b;
        this.bits = 8;
      }

      int bitsToCopy = this.bits < remainingCount ? this.bits : remainingCount;

      int sourceShift = this.bits - bitsToCopy;

      int targetShift = remainingCount - bitsToCopy;

      result |= this.lastByte >> sourceShift << targetShift;

      remainingCount -= bitsToCopy;
      this.bits -= bitsToCopy;

      this.lastByte &= 255 >> 8 - this.bits;
    }
    return result;
  }

  public long readLongBits(int count) throws IOException {
    if (count == 0) {
      throw new IllegalArgumentException("Cannot read 0 bits.");
    }
    if (count > 64) {
      throw new IllegalArgumentException("Bit count overflow: " + count);
    }

    long result = 0L;

    int remainingCount = count;
    while (remainingCount > 0)
    {
      if (this.bits == 0) {
        int b = this.in.read();
        if (b < 0) {
          throw new IOException("End of stream reached.");
        }
        this.lastByte = b;
        this.bits = 8;
      }

      int bitsToCopy = this.bits < remainingCount ? this.bits : remainingCount;

      int sourceShift = this.bits - bitsToCopy;

      int targetShift = remainingCount - bitsToCopy;

      result |= this.lastByte >> sourceShift << targetShift;

      remainingCount -= bitsToCopy;
      this.bits -= bitsToCopy;

      this.lastByte &= 255 >> 8 - this.bits;
    }

    return result;
  }

  public void close() {
    try {
      this.in.close();
    } catch (IOException ex) {
      System.out.println("Error while closing bit stream: " + ex.toString());
    }
  }
}