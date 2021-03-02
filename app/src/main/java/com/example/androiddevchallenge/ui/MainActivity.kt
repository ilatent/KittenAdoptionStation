/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.GlobalApp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.KittenInfo
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(mainViewModel) { info ->
                    val intent = Intent(this, KittenDetailActivity::class.java).apply {
                        putExtra("data", info)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun MyApp(mainViewModel: MainViewModel, onClick: (KittenInfo) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                val title = "Choose a cat you like"
                TopAppBar(
                    title = { Text(text = title) }
                )
            },

            content = {
                KittenFeedUI(mainViewModel.kittenList, onClick)
            }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun KittenFeedUI(list: List<KittenInfo>, onClick: (KittenInfo) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        cells = GridCells.Fixed(2)
    ) {
        items(list) { info ->
            KittenCard(info, onClick)
        }
    }
}

@Composable
private fun KittenCard(info: KittenInfo, onClick: (KittenInfo) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick(info) })
        ) {
            val imageIdentity = GlobalApp.context.resources.getIdentifier(
                info.imageUrl, "drawable",
                GlobalApp.context.packageName
            )
            val image: Painter = painterResource(imageIdentity)
            Image(
                painter = image,
                contentDescription = info.nickname,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = info.nickname,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = info.introduction,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = info.releaseTime,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End
                )
            }
        }

    }
}

//@Preview("Light Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun LightPreview() {
//    MyTheme {
//        MyApp()
//    }
//}
//
//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview() {
//    MyTheme(darkTheme = true) {
//        MyApp()
//    }
//}
