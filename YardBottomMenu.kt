package com.example.roro_tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun YardBottomMenu(
    isMenuExpanded: Boolean,
    onToggleMenu: () -> Unit,
    isSlotSelected: Boolean,
    onRegisterEmpty: () -> Unit,
    onRegisterPark: () -> Unit,
    isDirectionDown: Boolean,
    onToggleDirection: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .border(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleMenu() }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isMenuExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Toggle Menu"
            )
        }
        HorizontalDivider()

        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = onRegisterEmpty,
                    enabled = isSlotSelected,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0BEC5)),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) { Text("空き登録", color = if (isSlotSelected) Color.Black else Color.Gray) }

                Button(
                    onClick = onRegisterPark,
                    enabled = isSlotSelected,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0BEC5)),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) { Text("駐車登録", color = if (isSlotSelected) Color.Black else Color.Gray) }
            }

            AnimatedVisibility(visible = isMenuExpanded) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = onToggleDirection,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    ) { Text(if (isDirectionDown) "進行方向: 下" else "進行方向: 上", color = Color.Black) }

                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA)),
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    ) { Text("保存", color = Color.Black) }
                }
            }
        }
    }
}