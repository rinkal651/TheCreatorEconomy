package com.example.thecreatoreconomy.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.thecreatoreconomy.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.thecreatoreconomy.models.ApiResponse
import com.example.thecreatoreconomy.models.Data
import com.example.thecreatoreconomy.models.Link
import com.example.thecreatoreconomy.ui.theme.*
import com.example.thecreatoreconomy.viewmodels.DashboardViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun LineChartComposable(chartData: Map<String, Int>) {
    val context = LocalContext.current

    val entries = chartData.entries.mapIndexed { index, entry ->
        Entry(index.toFloat(), entry.value.toFloat())
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = {
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                description.isEnabled = false
            }
        },


        update = { lineChart ->
            // Set the gradient drawable as the fill drawable
            val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.gradient)
            val dataSet = LineDataSet(entries, "Overview").apply {
                color =  ContextCompat.getColor(context, R.color.blue_200)
                valueTextColor = ContextCompat.getColor(context, R.color.black)
                fillDrawable = drawable
                setDrawFilled(true)
            }
            lineChart.data = LineData(dataSet)

            // Configure Y-Axis
            val yAxis: YAxis = lineChart.axisLeft
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 1f

            // Configure X-Axis
            val xAxis: XAxis = lineChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = object : ValueFormatter() {
                private val months = arrayOf(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                )

                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return months.getOrNull(value.toInt()) ?: value.toString()
                }
            }
            xAxis.labelCount = 12

            // Remove right Y-axis
            lineChart.axisRight.isEnabled = false

            // Remove description label
            lineChart.description = Description().apply { text = "" }


            lineChart.invalidate() // Refresh the chart
        }
    )
}



@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem.Links,
        BottomNavItem.Courses,
        BottomNavItem.Campaigns,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selected = false,
                onClick = {
                        // Your Links Screen
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.imageId),
                        contentDescription = item.title,
                        modifier = Modifier.size(28.dp)
                    )
                },
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}

@Composable
fun CenterFloatingActionButton() {
    FloatingActionButton(
        onClick = { /* Handle onClick */ },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}


sealed class BottomNavItem(var title: String, var imageId: Int) {
    object Links : BottomNavItem("Links", R.drawable.mail)
    object Courses : BottomNavItem("Courses", R.drawable.files)
    object Campaigns : BottomNavItem("Campaigns", R.drawable.media )
    object Profile : BottomNavItem("Profile", R.drawable.tabbar_item )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen() {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val apiResponseData: State<ApiResponse?> = dashboardViewModel.apiData.collectAsState()

    if (apiResponseData.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...", style = MaterialTheme.typography.h3)
        }
    } else {
        Scaffold(
            bottomBar = { BottomNavigationBar() },
            floatingActionButton = { CenterFloatingActionButton() },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            dashboardBody(
                userName = "Ajay Manva",
                apiResponse = apiResponseData.value as ApiResponse
            )
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier.height(100.dp),
        title = { Text("Dashboard") },
        actions = {
            IconButton(onClick = { /* Handle click */ }) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    contentDescription = "setting",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    )
}

@Composable
fun dashboardBody(userName: String, apiResponse: ApiResponse) {
    val currentHour = SimpleDateFormat("HH", Locale.getDefault()).format(Date()).toInt()
    val greeting = when (currentHour) {
        in 0..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
    Column(modifier = Modifier
        .background(
            Color(0xFF0E6FFF)
        )
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        TopBar()
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(backgrey),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(backgrey),
                horizontalAlignment = Alignment.Start
            ) {
                Text(greeting, fontSize = 20.sp)
                userText(userName = userName)
                Spacer(modifier = Modifier.height(16.dp))
                ChartSection(chartData = apiResponse.data.overall_url_chart)
                Spacer(modifier = Modifier.height(16.dp))
                StatisticsSection(todaysClick = apiResponse.today_clicks, topLocation = apiResponse.top_location, topSource = apiResponse.top_source)
                Spacer(modifier = Modifier.height(16.dp))
                viewAnalytics("View analytics", drawableResId = R.drawable.arrows)
                Spacer(modifier = Modifier.height(16.dp))
                LinksSection(apiResponse.data)
                Spacer(modifier = Modifier.height(16.dp))
                viewAnalytics("View all Links", drawableResId = R.drawable.mail)
                Spacer(modifier = Modifier.height(16.dp))
                contactCard("Talk with us", drawableResId = R.drawable.whatsapp, color = green)
                Spacer(modifier = Modifier.height(16.dp))
                contactCard("Frequently Asked Questions", drawableResId = R.drawable.gethelp, color = lightBlue)
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun viewAnalytics(text: String, drawableResId: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgrey),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .background(backgrey), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = text,
                modifier = Modifier.size(28.dp)
            )
            Text(text, fontSize = 18.sp)
            // Placeholder for Chart
        }
    }
}


@Composable
fun contactCard(text: String, drawableResId: Int , color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color.copy(alpha = 0.1F)),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .background(color.copy(alpha = 0.1F)), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = text,
                modifier = Modifier
                    .size(38.dp)
                    .padding(start = 18.dp)
            )
            Text(text, fontSize = 18.sp, modifier = Modifier
                .padding(7.dp))
            // Placeholder for Chart
        }
    }
}

@Composable
fun userText(userName: String) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end= 16.dp))
        Image(
            painter = painterResource(id = R.drawable.shaking_hand),
            contentDescription = "Shaking Hand Icon",
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun ChartSection(chartData: Map<String, Int>?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Overview", fontSize = 14.sp, modifier = Modifier.padding(bottom = 10.dp))
            Spacer(modifier = Modifier.height(8.dp))
            // Placeholder for Chart

            if(chartData?.isEmpty() == false) {
                LineChartComposable(chartData = chartData)
            } else {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),

                )
            }
        }
    }
}

@Composable
fun StatisticsSection(todaysClick: Int, topLocation: String, topSource: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatisticCard(label = "Today's clicks", value = todaysClick.toString(), drawableResId = R.drawable.click)
        StatisticCard(label = "Top Location", value = topLocation, drawableResId = R.drawable.location)
        StatisticCard(label = "Instagram", value = topSource, drawableResId = R.drawable.globe)
    }
}

@Composable
fun StatisticCard(drawableResId: Int, label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(label, fontSize = 14.sp, color = Color.Gray)
        }
    }
}


@Composable
fun TabLayout(data: Data) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Top Links", "Recent Links")

    Column() {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            TabTheme {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .width(300.dp)
                        .background(grey.copy(alpha = 0.1F))
                        .height(40.dp),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .background(grey),
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .background(
                                    color = if (selectedTabIndex == index) tab_blue else white,
                                    shape = RoundedCornerShape(50.dp)
                                ),
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTabIndex == index) white else grey
                                )
                            }
                        )
                    }
                }

                Box(modifier = Modifier.border(border = BorderStroke(1.dp, grey))) {
                    Image(painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable { println("Button Clicked!") })
                }
            }
        }
        when (selectedTabIndex) {
            0 -> TabContent(data.top_links)
            1 -> TabContent(data.recent_links)
        }
    }
}


@Composable
fun TabContent(linkList: List<Link>) {
    linkList.forEach {
        LinkItem(webLink = it.web_link, imageLink = it.original_image, title = it.title, date = it.created_at, clicks = it.total_clicks)
    }
}


@Composable
fun LinksSection(data: Data) {
    Column {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TabLayout(data)
        }
    }
}


@Composable
fun LinkItem(webLink: String, imageLink : String?, title: String, date: String, clicks: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(fraction = 0.1f),
                    horizontalAlignment = Alignment.Start
                ) {

                    if (imageLink != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageLink),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(horizontal = 5.dp), horizontalAlignment = Alignment.Start
                ) {
                    Text(title, fontSize = 14.sp, maxLines = 1)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(date, fontSize = 12.sp, color = Color.Gray)
                }

                Column(
                    modifier = Modifier.fillMaxWidth(fraction = 0.2f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        clicks.toString(),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Clicks", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                // Draw a background
                .background(
                    color = Color(0xFFE8F1FF),
                )
                // Draw the dashed border
                .dashedBorder(
                    color = tab_blue,
                    shape = RectangleShape
                )
                // Add space between the border and the textField content
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically

            , ) {
                Text(webLink, color = tab_blue,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Image(
                    painter = painterResource(id = R.drawable.files),
                    contentDescription = "text",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
