package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Category
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor


@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    list: List<Category>,
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimens.paddingHorizontal,
                ),
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            list.forEachIndexed { index, category ->
                SpacerWidthMediumLarge()
                CategoryItem(
                    categoryResponse = category,
                    onCategoryClick = onCategoryClick
                )
                if (index == list.size - 1) {
                    SpacerWidthMediumLarge()
                }
            }
        }
    }
}


@Composable
fun CategoryItem(
    categoryResponse: Category,
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(AppTheme.dimens.categoryWidth)
            .height(AppTheme.dimens.categoryHeight)
            .clip(RoundedCornerShape(Constants.CORNER_RADUIES.dp))
            .clickable {
                onCategoryClick(categoryResponse.id, categoryResponse.name)
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = categoryResponse.image,
                contentScale = ContentScale.FillBounds,
                contentDescription = stringResource(R.string.category_image)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Black.copy(0.3f))
                .fillMaxSize()
                .zIndex(2f),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.small)
                .zIndex(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = categoryResponse.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = CustomColor.White,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}








