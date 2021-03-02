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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.GlobalApp
import com.example.androiddevchallenge.model.KittenInfo
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.KEY_DATA

class KittenDetailActivity : AppCompatActivity() {

    private val mViewModel by viewModels<KittenDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.kittenInfo = intent?.getSerializableExtra(KEY_DATA) as KittenInfo
        setContent {
            MyTheme {
                KittenInfoUI(mViewModel) {
                    finish()
                }
            }
        }
    }
}

@Composable
private fun KittenInfoUI(viewModel: KittenDetailViewModel, navigateBack: () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                val title = viewModel.kittenInfo.nickname
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {

                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "LOGO"
                            )
                        }
                    }
                )
            },

            content = {
                val imageIdentity = GlobalApp.context.resources.getIdentifier(
                    viewModel.kittenInfo.imageUrl, "drawable",
                    GlobalApp.context.packageName
                )
                val image: Painter = painterResource(imageIdentity)
                Column {
                    Image(
                        painter = image,
                        contentDescription = viewModel.kittenInfo.nickname,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = viewModel.kittenInfo.nickname,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = viewModel.kittenInfo.releaseTime,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier
                                .padding(top = 8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = viewModel.kittenInfo.introduction,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        )
    }
}
