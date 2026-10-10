package com.yikun.rclone.mainevent

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Back
import top.yukonga.miuix.kmp.icon.extended.Contacts
import top.yukonga.miuix.kmp.icon.extended.More
import top.yukonga.miuix.kmp.icon.extended.Settings
import top.yukonga.miuix.kmp.icon.extended.VerticalSplit
import top.yukonga.miuix.kmp.overlay.OverlayDialog
import top.yukonga.miuix.kmp.theme.LocalDismissState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.window.WindowDialog

@Preview
@Composable
fun MainEvent() {
    val pages = listOf("首页", "我的", "设置")
    val icons = listOf(MiuixIcons.VerticalSplit, MiuixIcons.Contacts, MiuixIcons.Settings)
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = MiuixScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "标题",
                largeTitle = "大标题", // 如果不指定，将使用 title 的值
                navigationIcon = {
                    IconButton(onClick = { /* 处理点击事件 */ }) {
                        Icon(MiuixIcons.Back, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(MiuixIcons.More, contentDescription = "更多")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                pages.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = icons[index],
                        label = label
                    )
                }
            }
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
                .overScrollVertical()
                .nestedScroll(scrollBehavior.nestedScrollConnection),

            ) {
            items(5) { card ->
                MyCard(
                    card,
                    onConfirmClick = { showDialog = true },
                    onCancelCLick = { showDialog = false })
                MyDialog(showDialog, onDismissRequest = { showDialog = false })
            }
        }
    }
}

@Composable
fun MyCard(index: Int, onConfirmClick: () -> Unit, onCancelCLick: () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp),
        insideMargin = PaddingValues(16.dp)
    ) {
        Text(
            text = "卡片$index",
            style = MiuixTheme.textStyles.title2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "这是卡片的详细内容描述，可以包含多行文本信息。"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                text = "取消",
                onClick = onCancelCLick
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                text = "确定",
                colors = ButtonDefaults.textButtonColorsPrimary(), // 使用主题颜色
                onClick = onConfirmClick
            )
        }
    }
}

@Composable
private fun MyDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    OverlayDialog(
        show = showDialog,
        onDismissRequest = onDismissRequest,
        title = "标题",
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    text = "Cancel",
                    onClick = onDismissRequest,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(20.dp))
                TextButton(
                    text = "Confirm",
                    onClick = onDismissRequest,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColorsPrimary(),
                )
            }
        }
    )
}

@Preview
@Composable
fun MainEvent1() {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold {
        TextButton(
            text = "显示对话框",
            onClick = { showDialog = true }
        )

        WindowDialog (
            title = "对话框标题",
            summary = "这是一个基本的对话框示例，可以包含各种内容。",
            show = showDialog,
            onDismissRequest = { showDialog = false }
        ) {
            TextButton(
                text = "确定",
                onClick = { showDialog = false },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}