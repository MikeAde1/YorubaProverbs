package example.yorubaproverbs

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.database.DatabaseReference
import example.yorubaproverbs.adapter.ProverbListAdapter
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.ui.about.AboutCover
import example.yorubaproverbs.ui.all.AllProverbs
import example.yorubaproverbs.ui.home.ProverbCategories
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @author Michael Adeneye
 * @receiver [MainActivity]
 */
class Main2Activity : ComponentActivity() {
    /**
     * The [PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null
    //private var tabLayout: TabLayout? = null

    //private MaterialSearchView materialSearchView;
    private val spinner: Spinner? = null
    private var databaseRef: DatabaseReference? = null
    private var recyclerView: RecyclerView? = null
    private var dataList: List<ProverbData> = ArrayList()
    private var searchAdapter: ProverbListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this
        setContent {
            YorubaProverbsTheme {
                Surface {
                    BaseActivityComposable()
                }
            }
        }
        //setContentView(R.layout.activity_main2)
        /*val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        tabLayout = findViewById(R.id.tabs)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager?.setAdapter(mSectionsPagerAdapter)
        mViewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))
        val cardView = findViewById<CardView>(R.id.cardview)
        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView?.setHasFixedSize(true)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@Main2Activity)
        recyclerView?.setLayoutManager(mLayoutManager)
        recyclerView?.setVisibility(View.GONE)
        setupAdapter()*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //spinner = findViewById(R.id.spinner);

        //materialSearchView = findViewById(R.id.searchView);
        /*materialSearchView.closeSearch();
        materialSearchView.setOnQueryTextListener(this);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //cardView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        recyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onSearchViewClosed() {
                //cardView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                dataList.clear();
                spinner.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
            }
        });*/
        /*val adapter = ArrayAdapter.createFromResource(
            this, R.array.language_array,
            R.layout.spinner_background
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        //spinner.setAdapter(adapter);
        if (!checkInternetConnection()) {
            Toast.makeText(
                this@Main2Activity,
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }*/

        /*findViewById<ComposeView>(R.id.baseComposeView).setContent {

        }*/
    }

    data class TabRowItem(
        val title: String,
        val icon: ImageVector? = null,
        val screen: (@Composable () -> Unit)? = null,
    )

    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
        ExperimentalMaterialApi::class
    )
    @Composable
    fun BaseActivityComposable() {
        // here we use LazyColumn that has build-in nested scroll, but we want to act like a
        // parent for this LazyColumn and participate in its nested scroll.
        // Let's make a collapsing toolbar for LazyColumn
        val toolbarHeight = 56.dp
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
        // our offset to collapse toolbar
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
        // now, let's create connection to the nested scroll system and listen to the scroll
        // happening inside child LazyColumn
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                    val newOffset = toolbarOffsetHeightPx.value + available.y //

                    toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                    // here's the catch: let's pretend we consumed 0 in any case, since we want
                    // LazyColumn to scroll anyway for good UX
                    // We're basically watching scroll without taking it
                    Log.d("result", toolbarHeightPx.toString() + " " + toolbarOffsetHeightPx.value.toString() + " " + available.y.toString())
                    return Offset.Zero
                }
            }
        }
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val shouldSearch = remember { mutableStateOf(false) }
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                // attach as a parent to the nested scroll system
                // makes the child composables scrollable with this configuration
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (shouldSearch.value.not()) {
                        androidx.compose.material3.TopAppBar(
                            title = {
                                Text(
                                    stringResource(id = R.string.title_name),
                                    style = TextStyle(
                                        color = colorResource(id = R.color.colorViewpager)
                                    )
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(id = R.color.colorPrimary)
                            ),
                            scrollBehavior = scrollBehavior,
                        )

                        androidx.compose.material3.IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                shouldSearch.value = true
                            },
                            content = {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "",
                                    tint = Color.LightGray
                                )
                            }
                        )
                    } else {
                        val searchText = remember {
                            mutableStateOf("")
                        }

                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(end = 30.dp),
                            value = searchText.value,
                            onValueChange = {
                                searchText.value = it
                            }
                        ) {
                            TextFieldDefaults.TextFieldDecorationBox(
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    end = 20.dp
                                ),
                                enabled = true,
                                innerTextField = it,
                                value = searchText.value,
                                singleLine = true,
                                interactionSource = remember { MutableInteractionSource() },
                                visualTransformation = VisualTransformation.None,
                                leadingIcon = {
                                    IconButton(
                                        onClick = {
                                            shouldSearch.value = false
                                        },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "",
                                                tint = Color.Gray
                                            )
                                        },
                                    )
                                }
                            )
                        }
                        androidx.compose.material3.IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                shouldSearch.value = true
                            },
                            content = {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "",
                                    tint = Color.Gray
                                )
                            },
                        )
                        var expanded by remember { mutableStateOf(false) }
                        var searchLanguage by remember { mutableStateOf("English") }

                        Box(modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterEnd)) {
                            Button(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(end = 30.dp),
                                onClick = { expanded = !expanded },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                elevation = null
                            ) {
                                Text(searchLanguage)
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                repeat(2) {
                                    DropdownMenuItem(
                                        onClick = {
                                            expanded = false
                                            searchLanguage = if (it == 1) {
                                                "English"
                                            } else {
                                                "Yoruba"
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = if (it == 1) "English" else "Yoruba",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            it.calculateTopPadding()
            val tabRowItems = listOf(
                TabRowItem(
                    title = stringResource(id = R.string.home),
                ),
                TabRowItem(
                    title = stringResource(id = R.string.all)
                ),
                TabRowItem(
                    title = stringResource(id = R.string.about)
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TabRow(
                    /*modifier = Modifier.offset {
                        IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt())
                    },*/
                    backgroundColor = colorResource(id = R.color.colorPrimary),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = colorResource(id = R.color.colorAccent)
                        )
                    },
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        index
                                    )
                                }
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = TextStyle(
                                        color = colorResource(id = R.color.colorViewpager)
                                    )
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f, true) // read doc for the use of this
                        /*.offset {
                            IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt())
                        }*/,
                    count = tabRowItems.size,
                    state = pagerState
                ) {
                    when (it) {
                        0 -> ProverbCategories()
                        1 -> AllProverbs(toolbarOffsetHeightPx.value.roundToInt())
                        2 -> AboutCover()
                    }
                }
            }
        }
    }


    @Preview
    @Composable
    fun BaseActivityComposablePrev() {
        Surface {
            BaseActivityComposable()
        }
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager = (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    }

    private fun setupAdapter() {
        dataList = ArrayList()
        searchAdapter = ProverbListAdapter(
            this@Main2Activity,
            dataList
        ) { position -> //Toast.makeText(Main2Activity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
            val intent = Intent(this@Main2Activity, ProverbDetails::class.java)
            intent.putExtra("value", dataList[position].content)
            intent.putExtra("translation", dataList[position].translation)
            intent.putExtra("explanation", dataList[position].explanation)
            startActivity(intent)
        }
        recyclerView!!.adapter = searchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main2, menu)
        //MenuItemCompat.setOnActionExpandListener(sea)
        val menuItem = menu.findItem(R.id.action_search) //search button
        /*materialSearchView.setMenuItem(menuItem);
        if (!materialSearchView.isSearchOpen()){
            spinner.setVisibility(View.GONE);
        }*/return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        return super.onOptionsItemSelected(item)
    }
    /*@Override
    public boolean onQueryTextSubmit(String query) {
        //final String option = spinner.getSelectedItem().toString();
        return false;
    }*/
    /**let the model class implement parceable
     * add arraylist to a bundle object (bundle.putParceableArraylist()
     * fragment.setArgument(bundle)
     * return fragment
     * and the fragment is called in the parent activity
     * get back data using getIntent.getExtras.getParceableArrayList(key)
     * for (ProverbData proverbData: proverbDataList){
     * if (proverbData.getContext().contains("typed value")){
     * Newarraylist.add(proverbData);
     * }
     * //when you want to search for more information from the proverb while listening to clicks, do
     * Newarraylist.get(getAdapterposition).getContent
     * Newarraylist.get(getAdapterposition).getTranslation
     * Newarraylist.get(getAdapterposition).getExplanation
     * } */
    /*@Override
    public boolean onQueryTextChange(final String newText) {
        final String option = spinner.getSelectedItem().toString();
        if (newText.length() > 25){
            spinner.setVisibility(View.GONE);
        }
        else{
            spinner.setVisibility(View.VISIBLE);
        }
        if(!newText.isEmpty()) {

                if (option.equals("English")){

                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String context = String.valueOf(snapshot.child("Translation").getValue());
                                //might use ignore case in this scenario
                                if (context.toLowerCase().contains(newText.toLowerCase())) {
                                    recyclerView.setVisibility(View.VISIBLE);

                                    ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                    dataList.add(proverbData);

                                }
                                Log.d("value", String.valueOf(dataList.size()));

                            }
                            searchAdapter.setProverbDataList(dataList);
                            searchAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else if (option.equals("Yorùbá")) {
                        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataList.clear();

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String context = String.valueOf(snapshot.child("Content").getValue());
                                        //might use ignore case in this scenario
                                        if (context.toLowerCase().contains(newText.toLowerCase())) {
                                            recyclerView.setVisibility(View.VISIBLE);

                                            ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                            dataList.add(proverbData);

                                        }

                                        Log.d("value", String.valueOf(dataList.size()));

                                    }
                                    searchAdapter.setProverbDataList(dataList);
                                    searchAdapter.notifyDataSetChanged();
                                }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                }
        }
        else {
            dataList.clear();
            recyclerView.setVisibility(View.GONE);
        }

        return false;
    }*/
    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {
        */
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    /*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        */
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    /*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            assert getArguments() != null;
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/
    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!
    ) {
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            when (position) {
                0 -> return HomeFragment()
                1 -> return All()
                2 -> return About()
            }
            return HomeFragment()
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.activity.ComponentActivity")
    )
    override fun onBackPressed() {
        /*if (materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
            spinner.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
        }else*/
        //else{
        super.onBackPressed()
        //}
    }
}