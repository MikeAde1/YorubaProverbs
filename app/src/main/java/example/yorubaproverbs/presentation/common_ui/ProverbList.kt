package example.yorubaproverbs.presentation.common_ui

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import example.yorubaproverbs.R
import example.yorubaproverbs.databinding.BottomSheetLayoutBinding
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.email.EmailActivity
import example.yorubaproverbs.util.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListLayout(list: List<ProverbData?>, containsFab: Boolean = true) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var clickedItemIndex by remember { mutableStateOf(0) }
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            if (list.isNotEmpty()) {
                AndroidViewBinding(
                    BottomSheetLayoutBinding::inflate
                ) {
                    proverb.text = list[clickedItemIndex]?.content
                    meaning.text = list[clickedItemIndex]?.translation
                    explanation.text = list[clickedItemIndex]?.explanation
                }
            }
        },
        sheetState = modalBottomSheetState,
        scrimColor = Color.Black.copy(alpha = 0.32f),
    ) {
        if (containsFab) {
            Scaffold(
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    context,
                                    EmailActivity::class.java
                                )
                            )
                        },
                        backgroundColor = colorResource(id = R.color.colorPrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.White
                        )
                    }
                }
            ) {
                ProverbCard(it, list, modalBottomSheetState, clipboardManager) { index ->
                    clickedItemIndex = index
                }
            }
        } else {
            ProverbCard(
                PaddingValues(vertical = 10.dp),
                list,
                modalBottomSheetState,
                clipboardManager
            ) { index ->
                clickedItemIndex = index
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProverbCard(
    paddingValues: PaddingValues,
    list: List<ProverbData?>,
    modalBottomSheetState: ModalBottomSheetState,
    clipboardManager: ClipboardManager,
    clickEvent: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 10.dp,
                horizontal = paddingValues.calculateRightPadding(LayoutDirection.Ltr)
            )
    ) {
        itemsIndexed(list) { index, item ->
            Card(
                shape = RoundedCornerShape(1.dp),
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .combinedClickable(
                        onClick = {
                            clickEvent(index)
                            coroutineScope.launch { modalBottomSheetState.show() }
                        },
                        onLongClick = {
                            clipboardManager.setText(AnnotatedString(item?.content.orEmpty()))
                            showToast(context, "Copied!")
                        }
                    )
            ) {
                Text(
                    text = item?.content.orEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    textAlign = TextAlign.Start,
                    color = Color.Gray,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}