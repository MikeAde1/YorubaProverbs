package example.yorubaproverbs

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import example.yorubaproverbs.adapter.ProverbListAdapter
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme

class CategorizedList : AppCompatActivity() {
    var dbref: DatabaseReference? = null
    private var linearLayout: LinearLayout? = null
    var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var proverbListAdapter: ProverbListAdapter? = null
    var category: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorized_list)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        dbref = firebaseDatabase.getReference("users")


            /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_if_arrow_back_1063891);*/
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248,253,254)));
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        category = intent.getStringExtra("name")
        supportActionBar?.elevation = 0f
        supportActionBar?.title = Html.fromHtml("<font color=\"#FFFFFF\">${category?.toTitleCase()}")

        /*progressBar = findViewById(R.id.pg)
        linearLayout = findViewById(R.id.waiting)
        recyclerView = findViewById(R.id.categoryList)*/
        recyclerView?.setHasFixedSize(true)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@CategorizedList)
        recyclerView?.setLayoutManager(mLayoutManager)
        //setUpAdapter()
        fetchFromDb()
    }

    private fun String.toTitleCase() =
        this.split("\\s".toRegex()).joinToString(" ") {
            it.lowercase().replaceFirstChar(Char::titlecase)
        }

    private fun setUpAdapter() {
        proverbListAdapter = ProverbListAdapter(
            this@CategorizedList,
            proverbDataList,
            ProverbListAdapter.Callback { position -> //Toast.makeText(getContext(), "Details coming soon", Toast.LENGTH_LONG).show();
                //this method overrides the onClick method in the adapter for the Viewholder
                //Toast.makeText(CategorizedList.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getContext(), ProverbDetails.class).putExtra(TEXT_KEY,Integer.toString(position)));
                /*Intent intent = new Intent(CategorizedList.this, ProverbDetails.class);
                intent.putExtra("name", proverbDataList.get(position).getExplanation());
                intent.putExtra("translation", proverbDataList.get(position).getTranslation());
                startActivity(intent);*/
                number = position
                /*DialogFragment.newInstance().show(
                    supportFragmentManager, DialogFragment.newInstance().tag
                )*/
            })
        recyclerView!!.adapter = proverbListAdapter
    }

    private fun fetchFromDb() {
        proverbDataList.clear()
        dbref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                when (category) {
                    "The Good Person".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("ìwà rere")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                    "The Good Life".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("ìgbé ayé rere")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                    "Relationship".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("Ìbáṣepọ̀")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                    "Human Nature".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("Ìwà ẹ̀dá")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                    "Rights and Responsibilities".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("ẹ̀tọ́")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                    "Truism".uppercase() -> for (snapshot: DataSnapshot in dataSnapshot.children) {
                        //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                        if (snapshot.child("Context").value.toString().contains("òtítọ́")) {
                            val proverbData = snapshot.getValue(
                                ProverbData::class.java
                            )
                            proverbDataList.add(proverbData)
                        }
                        Log.d("value", proverbDataList.size.toString())
                    }
                }

                // initiate compose here
                /*proverbListAdapter!!.setProverbDataList(proverbDataList)
                proverbListAdapter!!.notifyDataSetChanged()*/
                findViewById<ComposeView>(R.id.categoryListComposeView).setContent {
                    YorubaProverbsTheme {
                        // A surface container using the 'background' color from the theme
                        Surface {
                            ProverbsListCover(proverbDataList)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        /*linearLayout!!.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE*/
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ProverbsListCover(proverbDataList: List<ProverbData?>) {
        LazyColumn(modifier = Modifier.wrapContentSize()) {
            itemsIndexed(proverbDataList) { index, item ->
                Card(
                    shape = RoundedCornerShape(1.dp),
                    elevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    onClick = {
                        number = index
                        /*DialogFragment.newInstance().show(
                            supportFragmentManager, DialogFragment.newInstance().tag
                        )*/
                        /*All.DialogFragment.newInstance().show(
                            childFragmentManager,
                            All.DialogFragment.newInstance().tag
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

    @Preview
    @Composable
    fun ProverbsListCoverPrev() {
        val data = ProverbData().apply {
            content = "content"
            context = "context"
            explanation = "Explanation"
            translation = "Translation"
        }
        val proverbDataList = mutableListOf(
            data,
            data,
            data,
            data
        )
        Surface {
            ProverbsListCover(proverbDataList)
        }
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
            proverb?.setText(
                proverbDataList[number]!!.content
            )
            meaning?.setText(
                proverbDataList[number]!!.translation
            )
            explanation?.setText(
                proverbDataList[number]!!.explanation
            )
            return rootview
        }

        companion object {
            fun newInstance(): DialogFragment {
                return DialogFragment()
            }
        }
    }
*/
    companion object {
        private var number = 0
        private val proverbDataList: MutableList<ProverbData?> = ArrayList()
    }
}