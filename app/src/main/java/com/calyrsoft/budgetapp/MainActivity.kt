package com.calyrsoft.budgetapp

import android.content.Context
import android.content.Intent
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.calyrsoft.budgetapp.ui.theme.InventoryappTheme
import com.calyrsoft.domain.Movie
import com.calyrsoft.data.MovieRepository
import com.calyrsoft.data.NetworkResult
import com.calyrsoft.framework.RemoteDataSource
import com.calyrsoft.framework.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class MainActivity : ComponentActivity() {
    private val scope = CoroutineScope(newSingleThreadContext("name"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = {
                    AppTopBar()
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add User"
                            )
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                        ),
                        actions = {
                            NavigationBarItem(
                                selected = true,
                                onClick = {  },
                                label = { Text("Home")},
                                icon = { Icon( imageVector = Icons.Rounded.Home, contentDescription = "Home Screen") })
                            NavigationBarItem(
                                selected = true,
                                onClick = {  },
                                label = { Text("Calendar")},
                                icon = { Icon( imageVector = Icons.Rounded.DateRange, contentDescription = "Home Screen") })
                            NavigationBarItem(
                                selected = true,
                                onClick = {  },
                                label = { Text("Profile")},
                                icon = { Icon( imageVector = Icons.Rounded.AccountCircle, contentDescription = "Home Screen") })
                        }
                    )
                }
            ) { paddingValues ->
                MainScreen(modifier = Modifier.padding(paddingValues))
            }

        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        MovieListView(Modifier.align(Alignment.Center))
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieListView(modifier: Modifier) {

    val mainViewModel : MainViewModel = viewModel()

    val mainUIStateTest by mainViewModel.movieUIState.collectAsStateWithLifecycle()
    mainViewModel.getMovies()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = mainUIStateTest.isLoading) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = mainUIStateTest.error != null) {
            Text(text = "Error" +mainUIStateTest.error ?: "")
        }
        AnimatedVisibility(visible = mainUIStateTest.movies.isNotEmpty()) {
//            FlowRow(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 100.dp),
//            ) {
//            repeat(mainUIStateTest.movies.count()) {
//                Text("Text $it")
//            }
            LazyVerticalGrid(modifier = modifier.padding(top = 60.dp, bottom = 100.dp), columns = GridCells.Fixed(2)) {
                items(mainUIStateTest.movies) { movie ->
                    ElevatedCard(onClick = { /*TODO*/ }, modifier = Modifier.padding(5.dp)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
//                                .height(60.dp)
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp,
                                )
//                                .clip(RoundedCornerShape(20.dp))
//                                .background(Color(0xFFC99DD3)),
                        ) {
                            Text( "${movie.title}")
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w185/${movie.posterPath}",
                                contentDescription = "Movie",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.FillWidth
                            )

                        }
                    }

                }
            }
        }
    }


}

fun updateUi(state: Any) {

}


fun updateUIState(it: Any): MainViewModel.MovieUIState {
    TODO("Not yet implemented")
}

//@Composable
//fun MovieListView(modifier: Modifier) {
//    LazyVerticalGrid( modifier = Modifier
//        .fillMaxSize()
//        .background(Color.Red),
//        contentPadding = PaddingValues(12.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalArrangement = Arrangement.spacedBy(10.dp),
//        columns = GridCells.Adaptive(minSize = 128.dp)
////        columns = GridCells.Fixed(3)
//        ) {
//        items(110) {
//            val isLightSquare = it % 2 == it % 2
//            val squareColor = if (isLightSquare) Color.Yellow else Red
//            Card(modifier = Modifier.padding(5.dp)){
//                Text(text = "Imagen$it")
//                Text(text =  "Description$it")
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(title = { Text(text = "My SUPER APP") }, colors =  TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
    ))
}




@Composable
fun MyApp() {
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Counter: $count", style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = { count++ }) {
            Text("Increment")
        }
//        Button(onClick = {
//           startLoginActivity(LocalContext.current)
//        }) {
//            Text(text = "To Login")
//        }
        Row {
            Text(text = "Android")
            Text(text = "Kotlin")
            Text(text = "Compose")
        }
        Box(modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center) {
            Icon(modifier = Modifier.size(80.dp),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = Color.Green)
            Text(text = "$count")
        }
//        LazyRow(
//            modifier = Modifier.fillMaxWidth().background(Color.LightGray)
//        ) {
//            items(30) {
//                Text( modifier = Modifier.padding(9.dp), text = "Item row $it")
//            }
//        }
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Green)
//        ) {
//            items(100) {
//                Text(modifier = Modifier.padding(8.dp), text = "Item number is $it")
//            }
//        }
//
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(9.dp),
            columns = GridCells.Fixed(3)) {
            items(100) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Item is $it")
            }

        }

//        LazyHorizontalGrid(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Blue)
//                .padding(9.dp),
//            rows = GridCells.Fixed(3)) {
//            items(100) {
//                Text(
//                    modifier = Modifier.padding(8.dp),
//                    text = "Item is $it")
//            }
//
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InventoryappTheme {
//        Greeting("Android")
//    }
//}

//fun startLoginActivity(context: Context) {
//    val i = Intent(context, LoginActivity::class.java)
//    context.startActivity(i)
//}