package org.example.pekko

import org.apache.pekko.util.ByteString
import org.apache.pekko.util.ByteString.ByteString1C

import java.io.{ByteArrayInputStream, InputStream}
import java.lang.invoke.{MethodHandles, MethodType}
import scala.util.Try

final object NewByteStringUtil {
  val byteStringInputStreamMethodTypeOpt = Try {
    val lookup = MethodHandles.publicLookup()
    val inputStreamMethodType = MethodType.methodType(classOf[InputStream])
    lookup.findVirtual(classOf[ByteString], "asInputStream", inputStreamMethodType)
  }.toOption

  def getInputStream(bs: ByteString): InputStream = {
    if (bs.isInstanceOf[ByteString1C]) {
      return getInputStreamUnsafe(bs)
    }
    byteStringInputStreamMethodTypeOpt.map { mh =>
      mh.invoke(bs).asInstanceOf[InputStream]
    }.getOrElse {
      legacyConvert(bs.compact)
    }
  }

  private def legacyConvert(bs: ByteString): InputStream = {
    if (bs.isInstanceOf[ByteString1C]) {
      return getInputStreamUnsafe(bs)
    }
    // NOTE: We actually measured recently, and compact + use array was pretty good usually
    legacyConvert(bs.compact)
  }

  private def getInputStreamUnsafe(bs: ByteString): InputStream =
    new ByteArrayInputStream(bs.toArrayUnsafe())
}
