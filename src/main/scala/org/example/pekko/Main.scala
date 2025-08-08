package org.example.pekko

import org.apache.commons.io.IOUtils
import org.apache.pekko.io.NewerByteStringUtil
import org.apache.pekko.util.ByteString

import java.nio.charset.StandardCharsets

object Main extends App {
  val byteString = ByteString("abc")
  println("byteStringInputStreamMethodTypeOpt? " + NewByteStringUtil.byteStringInputStreamMethodTypeOpt)
  val inputStream = NewByteStringUtil.getInputStream(byteString)
  println(IOUtils.toString(inputStream, StandardCharsets.UTF_8))
  val inputStream2 = NewerByteStringUtil.getInputStream(byteString)
  println(IOUtils.toString(inputStream2, StandardCharsets.UTF_8))

  val buf = MemorySegmentBuffer.createByteBufferViaMethodHandle(1024)
  println(buf)
}