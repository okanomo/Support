package com.example.roro_tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NumberPosition { LEFT, RIGHT }
enum class CellDirection { LTR, RTL }

data class ColumnLayoutConfig(
    val numberPosition: NumberPosition = NumberPosition.RIGHT,
    val cellDirection: CellDirection = CellDirection.LTR,
    val cellWeights: List<Float> = listOf(0.3f, 0.7f)
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YardListContent(
    paddingValues: PaddingValues,
    pagerState: PagerState,
    slotRows: List<Pair<Int?, Int?>>,
    selectedSlotId: Int?,
    onSlotSelected: (Int) -> Unit,
    getSlotData: (Int) -> SlotData,
    leftConfig: ColumnLayoutConfig = ColumnLayoutConfig(),
    rightConfig: ColumnLayoutConfig = ColumnLayoutConfig()
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.padding(paddingValues).fillMaxSize()
    ) { page ->
        val currentConfig = if (page == 0) leftConfig else rightConfig

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            items(slotRows) { rowPair ->
                val slotNumber = if (page == 0) rowPair.first else rowPair.second

                if (slotNumber != null) {
                    val slotData = getSlotData(slotNumber)
                    SlotItem(
                        slotData = slotData,
                        isSelected = selectedSlotId == slotNumber,
                        config = currentConfig,
                        onClick = { onSlotSelected(slotData.id) }
                    )
                } else {
                    Spacer(modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun SlotItem(
    slotData: SlotData,
    isSelected: Boolean,
    config: ColumnLayoutConfig,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick(slotData.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (config.numberPosition == NumberPosition.LEFT) {
            SlotNumberText(slotData.id.toString())
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(if (isSelected) BorderStroke(2.dp, Color.Red) else BorderStroke(1.dp, Color.Gray))
        ) {
            val displayCells = if (config.cellDirection == CellDirection.RTL) slotData.cells.reversed() else slotData.cells
            val displayWeights = if (config.cellDirection == CellDirection.RTL) config.cellWeights.reversed() else config.cellWeights

            displayCells.forEachIndexed { index, cellText ->
                val weight = displayWeights.getOrNull(index) ?: 1f
                Box(
                    modifier = Modifier
                        .weight(weight)
                        .fillMaxHeight()
                        .border(1.dp, Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(cellText)
                }
            }
        }

        if (config.numberPosition == NumberPosition.RIGHT) {
            SlotNumberText(slotData.id.toString())
        }
    }
}

@Composable
fun SlotNumberText(text: String) {
    Text(
        text = text,
        modifier = Modifier.width(48.dp),
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}