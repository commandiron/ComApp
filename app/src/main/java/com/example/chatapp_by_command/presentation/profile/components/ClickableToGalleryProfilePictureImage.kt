package com.example.chatapp_by_command.presentation.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.chatapp_by_command.R

@Composable
fun ClickableToGalleryProfilePictureImage(profilePictureUrlForCheck: String, onSelect:(Uri?) -> Unit = {}) {
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onSelect(uri)
    }

    Box {
        LaunchedEffect(key1 = imageUri){
            if(imageUri != null){
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, imageUri)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, imageUri!!)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
            }
        }

        if(bitmap != null){
            Image(painter = rememberImagePainter(bitmap),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { launcher.launch("image/*") }
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop)
        }else{
            if(profilePictureUrlForCheck != ""){
                Image(painter = rememberImagePainter(profilePictureUrlForCheck),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { launcher.launch("image/*") }
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop)
            }else{
                Image(painter = rememberImagePainter(
                    request = ImageRequest.Builder(context).data(R.drawable.ic_launcher_background)
                        .build()),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { launcher.launch("image/*") }
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Fit)
            }
        }
    }
}