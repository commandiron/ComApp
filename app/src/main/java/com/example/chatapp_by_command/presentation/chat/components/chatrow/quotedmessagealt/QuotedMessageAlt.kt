package com.example.chatapp_by_command.presentation.chat.components.chatrow.quotedmessagealt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Quoted message row which might contain title and text or title and image.
 *
 * If image is used [QuoteImageRow] places image to end of this row no matter how wide
 * quoted message row is.
 */
@Composable
fun QuotedMessageAlt(
    modifier: Modifier = Modifier,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
) {
    val color = remember { getRandomColor() }

//    println("🤔 QuotedMessageAlt() color: $color")
    QuoteImageRow(modifier = modifier,
        content = {
            Row {

                Surface(
                    color = color,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                ) {
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .wrapContentHeight()
                ) {
                    Text(
                        "You", color = color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        letterSpacing = 1.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (quotedImage != null) {
                                Icon(
                                    imageVector = Icons.Default.InsertPhoto,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }

                            Text(
                                text = quotedMessage ?: "Photo",
                                fontSize = 12.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
            }
        },
        image = {
            if (quotedImage != null) {
                Image(
                    painter = painterResource(id = quotedImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .layoutId("image")
                        .size(60.dp)
                        .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                )
            }
        }
    )
}