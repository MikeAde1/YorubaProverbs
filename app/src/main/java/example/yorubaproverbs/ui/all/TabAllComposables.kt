package example.yorubaproverbs.ui.all

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import example.yorubaproverbs.All
import example.yorubaproverbs.EmailActivity
import example.yorubaproverbs.Main2Activity
import example.yorubaproverbs.databinding.BottomSheetLayoutBinding
import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.*

class FirebaseQuery(val context: Context) {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseRef = firebaseDatabase.getReference("users")
    val proverbDataList = mutableListOf<ProverbData?>()

    fun getProverbList(onAccept: (MutableList<ProverbData?>) -> Unit) {
        //runBlocking {
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    proverbDataList.clear()

                    //Toast.makeText(context, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val proverbData = dataSnapshot1.getValue(
                            ProverbData::class.java
                        )
                        proverbDataList.add(proverbData)
                    }

                    onAccept(proverbDataList)
                    //future.complete(proverbDataList)

                    Log.d("value::", proverbDataList.size.toString())
                    // proverbListAdapter?.setProverbDataList(proverbDataList)
                    // proverbListAdapter?.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //Toast.makeText(context, databaseError.toString(), Toast.LENGTH_SHORT).show()
                    databaseError.toException().printStackTrace()
                }
            })
            //return proverbDataList
       // }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllProverbs(toolbarHeightPx: Int) {
    val context = LocalContext.current
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()
    val list = remember { mutableStateOf((listOf<ProverbData?>())) }
    val me = remember { mutableStateOf(false) }
    val number = remember { mutableStateOf(0) }
    if (!me.value) {
        FirebaseQuery(context).getProverbList {
            it?.let { it1 ->
                if (list.value.isEmpty()) {
                    list.value = it1
                    me.value = true
                    Log.d("value", it1.toString())
                    return@let
                }
            }
        }
    }
    if (me.value) {
        ListLayout(context, modalBottomSheetState, coroutineScope, list.value, number, toolbarHeightPx)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListLayout(
    context: Context,
    modalBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    list: List<ProverbData?>,
    number: MutableState<Int>,
    toolbarHeightPx: Int
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            if (list.isNotEmpty())
                    AndroidViewBinding(BottomSheetLayoutBinding::inflate,modifier = Modifier.padding ( top = 56.dp )) {
                        proverb.text = list[number.value]?.content
                        meaning.text = list[number.value]?.translation
                        explanation.text = list[number.value]?.explanation
                    }
            else Toast.makeText(context, "No proverbs available", Toast.LENGTH_SHORT).show()
        },
        sheetState = modalBottomSheetState,
        scrimColor = Color.Black.copy(alpha = 0.32f),
    ) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { context.startActivity(Intent(context, EmailActivity::class.java)) },
                backgroundColor = colorResource(id = example.yorubaproverbs.R.color.colorPrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White,
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(list) { index, item ->
                    Card(
                        shape = RoundedCornerShape(1.dp),
                        elevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        onClick = {
                            number.value = index
                            coroutineScope.launch { modalBottomSheetState.show() }
                        }
                    ) {
                        Text(
                            text = item?.content.orEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            textAlign = TextAlign.Start,
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun AllProverbsPreview() {
    val data = ProverbData().apply {
        content = "content"
        context = "context"
        explanation = "Explanation"
        translation = "Translation"
    }
    All.proverbDataList = mutableListOf(
        data,
        data,
        data,
        data
    )
    val context = LocalContext.current
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val number = remember { mutableStateOf(0) }
    Surface {
        ListLayout(
            context,
            modalBottomSheetState,
            coroutineScope,
            All.proverbDataList,
            number,
            147
        )
    }
}