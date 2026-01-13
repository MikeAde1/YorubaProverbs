package example.yorubaproverbs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import example.yorubaproverbs.ui.home.CategoriesItem
import example.yorubaproverbs.ui.home.list
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme

class HomeFragment : Fragment() {
    var homeView: View? = null
    var progressBar: ProgressBar? = null
    var dbref: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        dbref = firebaseDatabase.getReference("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeView = inflater.inflate(R.layout.fragment_home, container, false)
        (homeView?.findViewById(R.id.composeView) as ComposeView).setContent {
            YorubaProverbsTheme {
                // A surface container using the 'background' color from the theme
                ProverbCategories()
            }
        }
        return homeView
    }

    @Composable
    fun ProverbCategories() {
        Surface {
            CategoryItems(list)
        }
    }

    @Composable
    private fun CategoryItems(categories: List<CategoriesItem>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp)
        ) {
            items(categories) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .clickable {
                            startActivity(
                                Intent(context, CategorizedList::class.java).putExtra(
                                    "name",
                                    it.name.uppercase()
                                )
                            )
                        }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.button)
                            .placeholder(R.drawable.app_img)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_short_text_black_24dp),
                            contentDescription = "",
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 7.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = it.name,
                                style = TextStyle(color = colorResource(id = R.color.colorPrimary))
                            )
                            Text(
                                text = "(${it.translation})",
                                style = TextStyle(
                                    color = colorResource(id = R.color.colorPrimary),
                                    fontWeight = FontWeight.ExtraBold
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
    fun ProverbCategoriesPreview() {
        val list = listOf(
            CategoriesItem(
                    name = "Good Person",
                    translation = "ENIYAN RERE"
                ),
                CategoriesItem(
                    name = "Good Person",
                    translation = "ENIYAN RERE"
                ),
                CategoriesItem(
                    name = "Good Person",
                    translation = "ENIYAN RERE"
                ),
                CategoriesItem(
                    name = "Good Person",
                    translation = "ENIYAN RERE"
                ),
                CategoriesItem(
                    name = "Good Person",
                    translation = "ENIYAN RERE"
                )
            )
        Surface {
            CategoryItems(list)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        val cardView = homeView!!.findViewById<ComposeView>(R.id.composeView)
        val cardView2 = homeView!!.findViewById<ComposeView>(R.id.composeView)
        val cardView3 = homeView!!.findViewById<ComposeView>(R.id.composeView)
        val cardView4 = homeView!!.findViewById<ComposeView>(R.id.composeView)
        val cardView5 = homeView!!.findViewById<ComposeView>(R.id.composeView)
        val cardView6 = homeView!!.findViewById<ComposeView>(R.id.composeView)
        cardView.setOnClickListener {
            startActivity(
                Intent(context, CategorizedList::class.java).putExtra(
                    "name",
                    "The Good Person"
                )
            )
            /*
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                if (String.valueOf(snapshot.child("Context").getValue()).contains("Good Person")){
                                    Intent
                                }
                            }
                        }
    
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
    
                        }
                    });
    */
        }
        cardView2.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CategorizedList::class.java
                ).putExtra("name", "The Good Life")
            )
        }
        cardView3.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CategorizedList::class.java
                ).putExtra("name", "Relationship")
            )
        }
        cardView4.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CategorizedList::class.java
                ).putExtra("name", "Human Nature")
            )
        }
        cardView5.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CategorizedList::class.java
                ).putExtra("name", "Rights and Responsibilities")
            )
        }
        cardView6.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CategorizedList::class.java
                ).putExtra("name", "Truism")
            )
        }
    }
}