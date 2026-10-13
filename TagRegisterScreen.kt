package com.example.roro_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TagRegisterScreen(viewModel: YardViewModel) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE0E0E0)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* 削除機能(現状はアクションなし想定) */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) { Text("削除") }

                Button(
                    onClick = { viewModel.saveTagAndReturnToDetail() }
                ) { Text("保存") }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("札登録", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Text("シャーシ番号", fontSize = 14.sp, color = Color.Gray)
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 向け先
            SectionTitle("向け先")
            TagGrid(listOf("仙台", "釧路", "名古", "苫小", "大阪"))

            Spacer(modifier = Modifier.height(16.dp))

            // 本船
            SectionTitle("本船")
            TagGrid(listOf("神永", "神北", "神明", "神泉", "神加", "神珠"))

            Spacer(modifier = Modifier.height(16.dp))

            // シャーシ長さ
            SectionTitle("シャーシ長さ")
            TagGrid(listOf("10m", "12m", "13m", "14m", "Om"))

            Spacer(modifier = Modifier.height(16.dp))

            // 飛び出し
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                SectionTitle("飛び出し")
                Text("高さ注意", color = Color.Red, modifier = Modifier.padding(end = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 精荷
            SectionTitle("精荷")
            TagGrid(listOf("危険", "指定", "空"))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp))
}

@Composable
fun TagGrid(items: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().heightIn(max = 120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { item ->
            Button(
                onClick = { /* 選択処理 */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0BEC5)),
                shape = MaterialTheme.shapes.small
            ) {
                Text(item, color = Color.Black)
            }
        }
    }
}