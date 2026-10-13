package com.example.roro_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReceptionScreen(viewModel: YardViewModel) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        // ヘッダーは必要に応じて使い回すか、別のものを指定
        topBar = { /* 省略 */ },
        
        // ★ ここだけ新しい「シャーシ情報」用のメニューに差し替える
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9)) // 画像のような少し緑がかった背景色
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            ) {
                Text("シャーシ情報", fontSize = 14.sp, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("シャーシ番号: xxx-xxxx")
                        Text("本船: OOOO")
                        Text("乗船予定日: 2026/10/08")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { /* マップ切り替え処理 */ }) {
                        Text("マップ切り替え")
                    }
                    Button(onClick = { /* 登録処理 */ }) {
                        Text("登録")
                    }
                }
            }
        }
    ) { paddingValues ->
        // ★ 真ん中のリストは以前作ったものを1行で使い回す！
        YardListContent(
            paddingValues = paddingValues,
            pagerState = pagerState,
            slotRows = viewModel.slotRows,
            selectedSlotId = viewModel.selectedSlotId,
            onSlotSelected = { id -> viewModel.selectedSlotId = id },
            getSlotData = { id -> viewModel.getSlot(id) }
        )
    }
}