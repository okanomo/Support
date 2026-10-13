package com.example.roro_tab

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

data class SlotData(
    val id: Int,
    val cells: List<String> = listOf("", "")
)

enum class Screen { YARD, INPUT, CHASSIS_DETAIL, TAG_REGISTER }

class YardViewModel : ViewModel() {
    private val _slotDb = mutableStateMapOf<Int, SlotData>()

    var selectedSlotId by mutableStateOf<Int?>(null)
    var isDirectionDown by mutableStateOf(true)
    var isMenuExpanded by mutableStateOf(false)
    var currentScreen by mutableStateOf(Screen.YARD)

    // 車番の一時保存用
    var tempCarNumber by mutableStateOf("")

    val MAX_SLOT_NUMBER = 48
    val SINGLE_COLUMN_THRESHOLD = 30

    val leftSequence = mutableListOf<Int>()
    val rightSequence = mutableListOf<Int>()
    val slotRows = mutableListOf<Pair<Int?, Int?>>()

    init {
        var currentNum = 1
        while (currentNum <= MAX_SLOT_NUMBER) {
            _slotDb[currentNum] = SlotData(id = currentNum)
            if (currentNum >= SINGLE_COLUMN_THRESHOLD) {
                slotRows.add(Pair(null, currentNum))
                rightSequence.add(currentNum)
                currentNum++
            } else {
                val left = currentNum
                val right = if (currentNum + 1 >= SINGLE_COLUMN_THRESHOLD) null else currentNum + 1
                slotRows.add(Pair(left, right))
                leftSequence.add(left)
                if (right != null) rightSequence.add(right)
                currentNum += if (right == null) 1 else 2
            }
        }
        selectedSlotId = leftSequence.firstOrNull()
    }

    fun getSlot(id: Int): SlotData = _slotDb[id] ?: SlotData(id)

    fun registerEmpty(currentPage: Int) {
        selectedSlotId?.let { id ->
            _slotDb[id] = _slotDb[id]!!.copy(cells = listOf("", ""))
            moveNext(currentPage)
        }
    }

    fun navigateToInput() {
        if (selectedSlotId != null) {
            currentScreen = Screen.INPUT
        }
    }

    // 車番選択 -> シャーシ詳細へ
    fun selectCarNumber(carNumber: String) {
        tempCarNumber = carNumber
        currentScreen = Screen.CHASSIS_DETAIL
    }

    fun navigateToTagRegister() {
        currentScreen = Screen.TAG_REGISTER
    }

    fun saveTagAndReturnToDetail() {
        currentScreen = Screen.CHASSIS_DETAIL
    }

    // シャーシ詳細の保存 -> 最初の画面に戻って枠に登録
    fun saveChassisDetailAndPark() {
        selectedSlotId?.let { id ->
            _slotDb[id] = _slotDb[id]!!.copy(cells = listOf("?", tempCarNumber))
            val isLeft = leftSequence.contains(id)
            moveNext(if(isLeft) 0 else 1)
        }
        currentScreen = Screen.YARD
    }

    // 削除 -> 車番入れずに最初の画面に戻る（進行方向は進まない）
    fun deleteChassisDetail() {
        tempCarNumber = ""
        currentScreen = Screen.YARD
    }

    fun cancelInput() {
        currentScreen = Screen.YARD
    }

    private fun moveNext(currentPage: Int) {
        val currentSequence = if (currentPage == 0) leftSequence else rightSequence
        val currentIndex = currentSequence.indexOf(selectedSlotId)
        if (currentIndex != -1) {
            val nextIndex = if (isDirectionDown) currentIndex + 1 else currentIndex - 1
            if (nextIndex in currentSequence.indices) {
                selectedSlotId = currentSequence[nextIndex]
            }
        }
    }
}