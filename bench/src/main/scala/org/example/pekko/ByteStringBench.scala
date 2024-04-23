package org.example.pekko

import org.apache.commons.io.IOUtils
import org.apache.pekko.util.ByteString
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
class ByteStringBench {

  private val rnd = new scala.util.Random
  private val simpleByteString = ByteString(rnd.nextString(1000))
  private var byteString: ByteString = _

  @Setup
  def setup(): Unit = {
    val builder = ByteString.newBuilder
    (0 to 1000).foreach { _ =>
      val str = rnd.nextString(1000)
      builder.append(ByteString(str))
    }
    byteString = builder.result()
  }

  @Benchmark
  def oldGetInputStream(blackhole: Blackhole): Unit = {
    val is = OldByteStringUtil.getInputStream(byteString)
    blackhole.consume(IOUtils.toByteArray(is))
  }

  @Benchmark
  def newGetInputStream(blackhole: Blackhole): Unit = {
    val is = NewByteStringUtil.getInputStream(byteString)
    blackhole.consume(IOUtils.toByteArray(is))
  }

  @Benchmark
  def oldGetInputStreamSimple(blackhole: Blackhole): Unit = {
    val is = OldByteStringUtil.getInputStream(simpleByteString)
    blackhole.consume(IOUtils.toByteArray(is))
  }

  @Benchmark
  def newGetInputStreamSimple(blackhole: Blackhole): Unit = {
    val is = NewByteStringUtil.getInputStream(simpleByteString)
    blackhole.consume(IOUtils.toByteArray(is))
  }
}
