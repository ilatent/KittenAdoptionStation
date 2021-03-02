package com.example.androiddevchallenge.model

import java.io.Serializable

data class KittenInfo(
    val nickname: String, //昵称
    val imageUrl: String, //生活照
    val introduction: String, //简介
    val releaseTime: String//发布时间
):Serializable

