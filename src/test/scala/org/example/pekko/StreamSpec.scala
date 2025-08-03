package org.example.pekko

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.scaladsl.{Keep, MergeHub, Sink, Source}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class StreamSpec extends AnyWordSpec with Matchers with BeforeAndAfterAll {
  private implicit val system: ActorSystem = ActorSystem("StreamSpec")

  override def afterAll(): Unit = {
    system.terminate()
  }

  "MergeHub" should {
    "work with long streams" in {
      val (sink, result) = MergeHub.source[Int](16).take(20000).toMat(Sink.seq)(Keep.both).run()
      Source(1 to 10000).runWith(sink)
      Source(10001 to 20000).runWith(sink)

      Await.result(result, 10.seconds).sorted should ===(1 to 20000)
    }
  }

}
