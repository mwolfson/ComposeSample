package com.example.mycomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String, val color: Color)

@Composable
fun OsCard(msg: Message) {


    Row(modifier = Modifier.background(MaterialTheme.colors.background)) {
        Image(
            painter = painterResource(id = R.drawable.vct_bugdroid),
            contentDescription = "The items",
            modifier = Modifier
                .size(72.dp)
                .padding(8.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.onBackground, CircleShape),
            colorFilter = ColorFilter.tint(msg.color)

        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded:                         Boolean by remember { mutableStateOf(true) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                msg.author,
                Modifier.padding(top = 8.dp),
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    msg.body,
                    Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            OsCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewList() {
    MyComposeAppTheme {
        Conversation(SampleData.conversationSample)
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    MyComposeAppTheme {
        OsCard(Message("Mike", "Cool", Color.Green))
    }
}