package com.example.yard.ui

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yard.ui.theme.YardTheme
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val TableLineColor = Color(0xFF8DAFC4)

@Composable
fun ListScreen() {
    val plateNumbers = listOf(
        "品川 300 あ 12-34", "横浜 580 か 56-78", "練馬 330 さ 90-12",
        "足立 500 た 34-56", "多摩 530 な 78-90", "品川 301 は 11-22",
        "横浜 581 ま 33-44", "練馬 331 や 55-66", "足立 501 ら 77-88",
        "多摩 531 わ 99-00", "世田谷 300 あ 10-20", "杉並 500 か 30-40",
        "板橋 330 さ 50-60", "江東 580 た 70-80", "葛飾 530 な 90-12"
    )
    var fontSizeSp by rememberSaveable { mutableStateOf(18f) }
    var isTableView by rememberSaveable { mutableStateOf(true) }
    var isSettingsExpanded by rememberSaveable { mutableStateOf(true) }
    var saveMessage by rememberSaveable { mutableStateOf("") }
    val textSize = fontSizeSp.sp
    val fontSizeName = when (fontSizeSp) {
        14f -> "小"
        18f -> "中"
        22f -> "大"
        else -> "カスタム"
    }
    val context = androidx.compose.ui.platform.LocalContext.current
    val saveLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) {
            saveSettings(context, uri, fontSizeName, textSize, isTableView) {
                saveMessage = it
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF7FBFF)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (isSettingsExpanded) Header(textSize)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    if (isSettingsExpanded) {
                        Spacer(Modifier.height(8.dp))
                        FontSizeSelector(fontSizeName, {
                            fontSizeSp = when (it) {
                                "小" -> 14f
                                "大" -> 22f
                                else -> 18f
                            }
                        }, textSize)
                        Text(
                            text = "フォントサイズ：${fontSizeSp.toInt()}sp",
                            modifier = Modifier.padding(top = 4.dp),
                            fontSize = (textSize.value - 2).sp,
                            color = Color(0xFF55728D)
                        )
                        Slider(
                            value = fontSizeSp,
                            onValueChange = { fontSizeSp = it },
                            valueRange = 12f..30f,
                            steps = 17,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isSettingsExpanded) "ナンバープレート一覧"
                            else "一覧（${fontSizeSp.toInt()}sp）",
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF17324D)
                        )
                        Button(
                            onClick = { isSettingsExpanded = !isSettingsExpanded },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF356581)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = if (isSettingsExpanded) "▲" else "▼",
                                fontSize = (textSize.value - 3).sp
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    ViewModeSelector(isTableView, { isTableView = it }, textSize)
                    Spacer(Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 88.dp),
                        verticalArrangement = if (isTableView) Arrangement.spacedBy(0.dp)
                        else Arrangement.spacedBy(10.dp)
                    ) {
                        if (isTableView) {
                            item { PlateTable(plateNumbers, textSize) }
                        } else {
                            itemsIndexed(plateNumbers) { index, plateNumber ->
                                PlateItem(index + 1, plateNumber, textSize)
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPAN)
                        .format(Date())
                    saveLauncher.launch("$timestamp.txt")
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                containerColor = Color(0xFF4F91BE),
                contentColor = Color.White
            ) {
                Text(text = "保存", fontSize = textSize)
            }
            if (saveMessage.isNotEmpty()) {
                Text(
                    text = saveMessage,
                    modifier = Modifier.align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 28.dp),
                    fontSize = (textSize.value - 2).sp,
                    color = Color(0xFF356581)
                )
            }
        }
    }
}

private fun saveSettings(
    context: Context,
    uri: Uri,
    fontSizeName: String,
    textSize: TextUnit,
    isTableView: Boolean,
    onResult: (String) -> Unit
) {
    val content = buildString {
        appendLine("文字サイズ: $fontSizeName")
        appendLine("フォントサイズ: ${textSize.value.toInt()}sp")
        appendLine("表示形式: ${if (isTableView) "表" else "カード"}")
        appendLine("表のサイズ: ${textSize.value.toInt()}sp")
        appendLine("カードのサイズ: ${textSize.value.toInt()}sp")
    }
    try {
        context.contentResolver.openOutputStream(uri)?.use {
            it.write(content.toByteArray(Charsets.UTF_8))
            onResult("保存しました")
        } ?: onResult("保存に失敗しました")
    } catch (exception: IOException) {
        onResult("保存に失敗しました")
    } catch (exception: SecurityException) {
        onResult("保存に失敗しました")
    }
}

@Composable
private fun ViewModeSelector(
    isTableView: Boolean,
    onTableViewChanged: (Boolean) -> Unit,
    textSize: TextUnit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("カード" to false, "表" to true).forEach { (label, tableMode) ->
            val isSelected = isTableView == tableMode
            Button(
                onClick = { onTableViewChanged(tableMode) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF4F91BE) else Color.White,
                    contentColor = if (isSelected) Color.White else Color(0xFF356581)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = label, fontSize = textSize)
            }
        }
    }
}

@Composable
private fun PlateTable(plateNumbers: List<String>, textSize: TextUnit) {
    Column(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, TableLineColor))
    ) {
        PlateTableRow(null, "ナンバープレート", textSize, true, true)
        plateNumbers.forEachIndexed { index, plateNumber ->
            PlateTableRow(index + 1, plateNumber, textSize, false, index < plateNumbers.lastIndex)
        }
    }
}

@Composable
private fun PlateTableRow(
    number: Int?,
    plateNumber: String,
    textSize: TextUnit,
    isHeader: Boolean = false,
    drawBottomLine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth().then(
            if (drawBottomLine) {
                Modifier.drawBehind {
                    drawLine(
                        TableLineColor,
                        androidx.compose.ui.geometry.Offset(0f, size.height - 0.5f),
                        androidx.compose.ui.geometry.Offset(size.width, size.height - 0.5f),
                        1.dp.toPx()
                    )
                }
            } else Modifier
        )
    ) {
        TableCell(number?.toString() ?: "No.", Modifier.weight(0.22f), textSize, isHeader, true)
        TableCell(plateNumber, Modifier.weight(0.78f), textSize, isHeader)
    }
}

@Composable
private fun TableCell(
    text: String,
    modifier: Modifier,
    textSize: TextUnit,
    isHeader: Boolean = false,
    drawEndLine: Boolean = false
) {
    Text(
        text = text,
        modifier = modifier.then(
            if (drawEndLine) {
                Modifier.drawBehind {
                    drawLine(
                        TableLineColor,
                        androidx.compose.ui.geometry.Offset(size.width - 0.5f, 0f),
                        androidx.compose.ui.geometry.Offset(size.width - 0.5f, size.height),
                        1.dp.toPx()
                    )
                }
            } else Modifier
        ).padding(horizontal = 10.dp, vertical = 13.dp),
        fontSize = textSize,
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        color = Color(0xFF17324D)
    )
}

@Composable
private fun Header(textSize: TextUnit) {
    Surface(color = Color(0xFFDCEFFC), shadowElevation = 2.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "テキスト確認モックアップ",
                fontSize = (textSize.value + 3).sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF17324D)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "見出し ${(textSize.value + 3).toInt()}sp / 本文 ${textSize.value.toInt()}sp / ボタン ${textSize.value.toInt()}sp",
                fontSize = (textSize.value - 2).sp,
                color = Color(0xFF55728D)
            )
        }
    }
}

@Composable
private fun FontSizeSelector(
    selected: String,
    onSelected: (String) -> Unit,
    textSize: TextUnit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("小", "中", "大").forEach { sizeName ->
            val isSelected = sizeName == selected
            Button(
                onClick = { onSelected(sizeName) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF4F91BE) else Color.White,
                    contentColor = if (isSelected) Color.White else Color(0xFF356581)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = sizeName,
                    fontSize = textSize,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun PlateItem(number: Int, plateNumber: String, textSize: TextUnit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = number.toString(),
                modifier = Modifier.padding(end = 16.dp),
                fontSize = textSize,
                color = Color(0xFF55728D)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plateNumber,
                    fontSize = textSize,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17324D)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListScreenPreview() {
    YardTheme { ListScreen() }
}
