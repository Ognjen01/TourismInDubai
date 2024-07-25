package com.ognjenlazic.tourismindubai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreen
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreenViewModel
import com.ognjenlazic.tourismindubai.ui.theme.TourismInDubaiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourismInDubaiTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}