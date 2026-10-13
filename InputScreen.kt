package com.example.roro_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun InputScreen(viewModel: YardViewModel) {
    var activeTab by remember { mutableStateOf(0) }
    var searchInput by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }

    val mockResults = listOf(
        "室蘭130あ5131",
        "品川500い5131",
        "横浜330う5131",
        "なにわ400え5131",
        "福岡580お5131"
    )

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color(0xFFE0E0E0))) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                        .background(if (activeTab == 0) Color.LightGray else Color.Transparent)
                        .clickable { activeTab = 0 },
                    contentAlignment = Alignment.Center
                ) { Text("タグ読み取り") }
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                        .background(if (activeTab == 1) Color.LightGray else Color.Transparent)
                        .clickable { activeTab = 1 },
                    contentAlignment = Alignment.Center
                ) { Text("車番入力") }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                if (activeTab == 0) {
                    Text("RFIDタグを\n読み取ってください", fontSize = 20.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(onClick = { viewModel.cancelInput() }) { Text("キャンセル") }
                } else {
                    OutlinedTextField(
                        value = searchInput,
                        onValueChange = { if (it.length <= 4) searchInput = it },
                        label = { Text("車番 (下4桁)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { if (searchInput.isNotEmpty()) showModal = true },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("検索") }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { viewModel.cancelInput() }) { Text("戻る") }
                }
            }
        }
    }

    if (showModal) {
        Dialog(onDismissRequest = { showModal = false }) {
            Surface(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("対象車番を選択", fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))
                    LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                        items(mockResults) { carNum ->
                            Text(
                                text = carNum,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showModal = false
                                        viewModel.selectCarNumber(carNum) // 車番選択でシャーシ詳細へ
                                    }
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                fontSize = 18.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            HorizontalDivider()
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showModal = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                        Text("再入力")
                    }
                }
            }
        }
    }
}