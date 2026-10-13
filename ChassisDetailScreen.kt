package com.example.roro_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChassisDetailScreen(viewModel: YardViewModel) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE0E0E0)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.deleteChassisDetail() }, // 削除処理
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) { Text("削除") }

                Button(
                    onClick = { viewModel.navigateToTagRegister() }, // 札登録へ
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text("札登録", color = Color.Black) }

                Button(
                    onClick = { viewModel.saveChassisDetailAndPark() } // 保存処理
                ) { Text("保存") }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("シャーシ詳細情報", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 32.dp))

            val details = listOf(
                "シャーシ番号" to viewModel.tempCarNumber,
                "本船" to "******",
                "乗船予定日" to "******",
                "航路" to "******",
                "予約番号" to "******",
                "荷主" to "******",
                "向け地" to "******",
                "INゲート 日時" to "******",
                "OUTゲート 日時" to "******",
                "..." to "******",
                "リマーク" to ""
            )

            details.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label)
                    Text(value, modifier = Modifier.width(120.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
        }
    }
}