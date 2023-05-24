package example.yorubaproverbs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import example.yorubaproverbs.adapter.ProverbListAdapter
import example.yorubaproverbs.databinding.FragmentAllBinding
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme
import java.util.concurrent.CompletableFuture

class All : Fragment() {
    private var proverbListAdapter: ProverbListAdapter? = null
    private var recyclerView: RecyclerView? = null
    //private var fb: FloatingActionButton? = null
    private var searching: LinearLayout? = null
    private var mail: LinearLayout? = null
    private var databaseRef: DatabaseReference? = null
    private val TEXT_KEY = "text_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRef = firebaseDatabase.getReference("users")
    }
    var added = true
    lateinit var rootview : View

    @SuppressLint("NewApi")
    val future = CompletableFuture<List<ProverbData?>>()

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_all, container, false)
        /*searching = rootview.findViewById(R.id.searching)
        mail = rootview.findViewById(R.id.coverVg)
        fb = rootview.findViewById(R.id.fab)
        recyclerView = rootview.findViewById(R.id.recyclerView)
        recyclerView?.setHasFixedSize(true)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView?.setLayoutManager(mLayoutManager)
        setUpFloactingAction()
        setupAdapter()
        fetchData()
        fbClickListener()*/
        fetchData()
        return rootview
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun AllProverbs(proverbList: List<ProverbData?>) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        startActivity(Intent(context, EmailActivity::class.java))
                    },
                    backgroundColor = colorResource(id = R.color.colorPrimary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.wrapContentSize()
                        .padding(vertical = 56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add FAB",
                        tint = Color.White,
                    )
                }
            }
        ) {
            Box(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(paddingValues = it)) {
                LazyColumn(modifier = Modifier.wrapContentSize()) {
                    itemsIndexed(proverbList) { index, item ->
                        Card(
                            shape = RoundedCornerShape(1.dp),
                            elevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            onClick = {
                                /*AndroidViewBinding(FragmentAllBinding::inflate) {
                                    val myFragment = fragmentContainerView.getFragment<MyFragment>()
                                    // ...
                                }*/
                                number = index
                                /*DialogFragment.newInstance().show(
                                    childFragmentManager,
                                    DialogFragment.newInstance().tag
                                )*/
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

    @Preview
    @Composable
    fun AllProverbsPreview() {
        val data = ProverbData().apply {
            content = "content"
            context = "context"
            explanation = "Explanation"
            translation = "Translation"
        }
        proverbDataList = mutableListOf(
            data,
            data,
            data,
            data
        )
        Surface {
            AllProverbs(proverbDataList)
        }
    }

    private fun fbClickListener() {
        //fb!!.setOnClickListener { startActivity(Intent(context, EmailActivity::class.java)) }
    }

    private fun setUpFloactingAction() {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                /*if (dy > 0 && fb!!.visibility == View.VISIBLE) {
                    fb!!.hide()
                } else if (dy < 0 && fb!!.visibility != View.VISIBLE) {
                    fb!!.show()
                }*/
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setupAdapter() {
        proverbDataList = ArrayList()
        proverbListAdapter = ProverbListAdapter(
            context,
            proverbDataList
        ) { position -> //Toast.makeText(getContext(), "Details coming soon", Toast.LENGTH_LONG).show();
            //this method overrides the onClick method in the adapter for the Viewholder
            //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getContext(), ProverbDetails.class).putExtra(TEXT_KEY,Integer.toString(position)));
            /*Intent intent = new Intent(getContext(), ProverbDetails.class);
                    intent.putExtra("name", proverbDataList.get(position).getExplanation());
                    intent.putExtra("translation", proverbDataList.get(position).getTranslation());
                    startActivity(intent);*/
            number = position
            assert(fragmentManager != null)
            //DialogFragment.newInstance().show(childFragmentManager, DialogFragment.newInstance().tag)
        }
        recyclerView!!.adapter = proverbListAdapter
    }

    private fun fetchData() {
        databaseRef!!.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                proverbDataList.clear()

                //Toast.makeText(getContext(), String.valueOf(dataSnapshot),Toast.LENGTH_LONG).show();
                for (dataSnapshot1 in dataSnapshot.children) {
                    val proverbData = dataSnapshot1.getValue(
                        ProverbData::class.java
                    )
                    proverbDataList.add(proverbData)
                }
                //future.complete(proverbDataList)

                Log.d("value::", proverbDataList.size.toString())
                //proverbListAdapter?.setProverbDataList(proverbDataList)
                //proverbListAdapter?.notifyDataSetChanged()
                (rootview.findViewById(R.id.allComposeView) as ComposeView).setContent {
                    YorubaProverbsTheme {
                        // A surface container using the 'background' color from the theme
                        Surface {
                            AllProverbs(proverbDataList)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        /*searching!!.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE*/
        Log.d("value::2", proverbDataList.size.toString())
    }

    /*class DialogFragment : BottomSheetDialogFragment() {
        var proverb: TextView? = null
        var meaning: TextView? = null
        var explanation: TextView? = null
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootview = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
            proverb = rootview.findViewById(R.id.proverb)
            meaning = rootview.findViewById(R.id.meaning)
            explanation = rootview.findViewById(R.id.explanation)
            proverb?.setText(proverbDataList[number]!!.content)
            meaning?.setText(proverbDataList[number]!!.translation)
            explanation?.setText(proverbDataList[number]!!.explanation)
            return rootview
        }

        companion object {
            fun newInstance(): DialogFragment {
                return DialogFragment()
            }
        }
    }*/

    companion object {
        var proverbDataList: MutableList<ProverbData?> = ArrayList()
        var number = 0
    }
}