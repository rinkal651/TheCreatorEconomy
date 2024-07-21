/*
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
fun Tabs(){
    Column(modifier = Modifier.padding(20.dp)) {
        ClickableTabs(selectedItem = 0, tabsList = listOf("List Item1","List Item2","List Item3"), onClick ={} )
    }
}

@Composable
fun ClickableTabs(selectedItem: Int, tabsList : List<String>,onClick: (Int) -> Unit) {
    val selectedItemIndex = remember{
        mutableStateOf(selectedItem)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(70.dp))
            .border(
                border = BorderStroke(
                    1.dp,
                    grey
                ), RoundedCornerShape(70.dp)
            )
            .height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabsList.forEachIndexed { index, s ->
                TabItem(isSelected = index == selectedItemIndex.value, text = s, Modifier.weight(0.5f)) {
                    selectedItemIndex.value = index
                    onClick.invoke(selectedItemIndex.value)
                }
            }
        }
    }
}

@Composable
fun TabItem(isSelected: Boolean, text: String, modifier: Modifier, onClick: () -> Unit) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Color.White
        } else {
            Color.Black
        },
        animationSpec = tween(easing = LinearEasing), label = ""
    )

    val background : Color by animateColorAsState(targetValue = if (isSelected)
        primaryBg
    else
        Color.White,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        , label = "")
    val border = if (isSelected)
        BorderStroke(
            1.dp,
            primaryColor
        )
    else
        BorderStroke(
            0.dp,
            Color.White
        )
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .background(background, RoundedCornerShape(70.dp))
            .border(
                border = border, RoundedCornerShape(70.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

                onClick.invoke()
            }
            .padding(vertical = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.titleSmall,
            textAlign = TextAlign.Center,
            color = tabTextColor
        )
    }
}
*/
