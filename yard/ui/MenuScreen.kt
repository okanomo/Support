package com.example.yard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(
    onListSelected: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF7FBFF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFDCEFFC),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = "メニュー",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17324D)
                )
            }
            Spacer(Modifier.height(32.dp))
            Button(
                // TODO: ここにマップ画面への遷移処理を書くと、マップ表示にできます
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F91BE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "マップ表示", fontSize = 22.sp)
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onListSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F91BE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "リスト表示", fontSize = 22.sp)
            }
        }
    }
}
