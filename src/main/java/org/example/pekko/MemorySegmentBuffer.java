package org.example.pekko;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;

public class MemorySegmentBuffer {
  public static ByteBuffer createByteBuffer(int size) {
    try (Arena arena = Arena.ofShared()) {
      MemorySegment segment = arena.allocate(size);
      segment.unload();
      return segment.asByteBuffer();
    }
  }

  private static MethodHandle arenaLookup;
  private static MethodHandle allocateMethod;
  private static MethodHandle asByteBufferMethod;

  static {
    try {
      // Load the Arena and MemorySegment classes dynamically
      Class<?> arenaClass = Class.forName("java.lang.foreign.Arena");
      Class<?> segmentClass = Class.forName("java.lang.foreign.MemorySegment");
      MethodHandles.Lookup lookup = MethodHandles.lookup();
      arenaLookup = lookup.findStatic(arenaClass, "ofShared", MethodType.methodType(arenaClass));
      allocateMethod = lookup.findVirtual(arenaClass, "allocate", MethodType.methodType(segmentClass, long.class));
      asByteBufferMethod = lookup.findVirtual(segmentClass, "asByteBuffer", MethodType.methodType(ByteBuffer.class));
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public static ByteBuffer createByteBufferViaMethodHandle(long size) {
    try (AutoCloseable arena = (AutoCloseable) arenaLookup.invoke()) {
      Object segment = allocateMethod.invoke(arena, size);
      return (ByteBuffer) asByteBufferMethod.invoke(segment);
    } catch (Throwable t) {
      throw new RuntimeException("Failed to create ByteBuffer via MethodHandle", t);
    }
  }
}
