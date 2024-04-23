package org.example.pekko

import org.apache.pekko.util.ByteString
import org.apache.pekko.util.ByteString.ByteString1C

import java.io.{ByteArrayInputStream, InputStream}

final object OldByteStringUtil {
  def getInputStream(byteString: ByteString): InputStream = {
    byteString match {
      case cs: ByteString1C =>
        new ByteArrayInputStream(cs.toArrayUnsafe())
      case _ =>
        getInputStream(byteString.compact)
    }
  }
}
