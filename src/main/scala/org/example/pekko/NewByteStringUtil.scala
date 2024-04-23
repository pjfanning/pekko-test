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

  def getInputStream(byteString: ByteString): InputStream = {
    byteString match {
      case cs: ByteString1C =>
        new ByteArrayInputStream(cs.toArrayUnsafe())
      case _ =>
        byteStringInputStreamMethodTypeOpt.map { mh =>
          mh.invoke(byteString).asInstanceOf[InputStream]
        }.getOrElse {
          val data = byteString.toArrayUnsafe()
          new ByteArrayInputStream(data)
        }
    }
  }
}
