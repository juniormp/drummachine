import java.io.File
import javax.sound.sampled.AudioSystem
import kotlinx.coroutines.*

class Beats {
    suspend fun playBeats(beats: String, file: String) {
        val parts = beats.split("x")
        var count = 0

        for (part in parts) {
            count += part.length + 1
            if (part == "") {
                playSound(file)
            } else {
                delay(100 * (part.length + 1L))
                if (count < beats.length) {
                    playSound(file)
                }
            }
        }
    }

    fun playSound(file: String) {
        val clip = AudioSystem.getClip()
        val audioInputStream = AudioSystem.getAudioInputStream(
            File(
                file
            )
        )
        clip.open(audioInputStream)
        clip.start()
    }
}


suspend fun main() {
    //coroutines1()
    coroutines2()
}

suspend fun coroutines1() {
    val beats = Beats()

    GlobalScope.launch {
        beats.playBeats("x-x-x-x-x-x", "toms.aiff")
    }

    beats.playBeats("x-------x-----", "crash_cymbal.aiff")
}

fun coroutines2() {
    val beats = Beats()

    runBlocking {
        launch {
            beats.playBeats("x-x-x-x-x-x------", "toms.aiff")
        }

        beats.playBeats("x---x---x-----", "crash_cymbal.aiff")
    }
}