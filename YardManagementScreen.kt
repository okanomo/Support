package com.example.roro_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun YardManagementScreen(viewModel: YardViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("A", "B", "C", "D", "枠外")
    val pagerState = rememberPagerState(pageCount = { 2 })

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFE0E0E0)).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("ヤード"); Spacer(modifier = Modifier.width(8.dp))
                        Text("品川埠頭", fontWeight = FontWeight.Bold)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                    Row {
                        Text("エリア"); Spacer(modifier = Modifier.width(8.dp))
                        Text("1野積場", fontWeight = FontWeight.Bold)
                    }
                }
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(selected = selectedTabIndex == index, onClick = { selectedTabIndex = index }, text = { Text(title) })
                    }
                }
            }
        },
        bottomBar = {
            YardBottomMenu(
                isMenuExpanded = viewModel.isMenuExpanded,
                onToggleMenu = { viewModel.isMenuExpanded = !viewModel.isMenuExpanded },
                isSlotSelected = viewModel.selectedSlotId != null,
                onRegisterEmpty = { viewModel.registerEmpty(pagerState.currentPage) },
                onRegisterPark = { viewModel.navigateToInput() },
                isDirectionDown = viewModel.isDirectionDown,
                onToggleDirection = { viewModel.isDirectionDown = !viewModel.isDirectionDown },
                onSave = { viewModel.isMenuExpanded = false }
            )
        }
    ) { paddingValues ->
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