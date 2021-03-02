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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.GlobalApp
import com.example.androiddevchallenge.R
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
