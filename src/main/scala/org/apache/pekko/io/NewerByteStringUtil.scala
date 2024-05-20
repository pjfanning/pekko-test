package org.apache.pekko.io

import org.apache.pekko.util.ByteString
import org.apache.pekko.util.ByteString.ByteStrings

import java.io.{ByteArrayInputStream, InputStream, SequenceInputStream}
import scala.collection.JavaConverters._

final object NewerByteStringUtil {

  def getInputStream(bs: ByteString): InputStream = bs match {
    case bss: ByteStrings =>
      new SequenceInputStream(bss.bytestrings.iterator.map(getInputStream).asJavaEnumeration)
    case _ =>
      new ByteArrayInputStream(bs.toArrayUnsafe())
  }
}
