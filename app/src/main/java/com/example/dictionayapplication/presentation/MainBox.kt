package com.example.dictionayapplication.presentation


import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainBox(
    mainState: MainState
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 30.dp)
        ) {
            mainState.wordItem?.let { wordItem ->
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = wordItem.word,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.onSecondary,
                                shape = CircleShape
                            )
                    ) {
                        IconButton(
                            onClick = { if (wordItem.audioUrl.isNotEmpty()){
                                playAudio(context,wordItem.audioUrl)
                            }

                            }) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Text(
                        text = wordItem.phonetic,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 110.dp)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp
                    )
                )
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.copy(0.7f)
                )
        ) {
            if (mainState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else (
                    mainState.wordItem?.let { wordItem -> WordResult(wordItem) }
                    )
        }
    }
}

fun playAudio(context: Context, audioUrl: String) {
    if (audioUrl.isBlank()) return // Early return if the URL is blank

    val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        // Prepend "https:" to the URL if it starts with "//"
        val fullUrl = if (audioUrl.startsWith("//")) "https:$audioUrl" else audioUrl
        setDataSource(fullUrl)
        prepareAsync()
        setOnPreparedListener { start() }
        setOnCompletionListener { release() }
    }
}
