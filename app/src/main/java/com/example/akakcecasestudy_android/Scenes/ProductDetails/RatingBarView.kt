package com.example.akakcecasestudy_android.Scenes.ProductDetails

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBarView(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color(0xFFFFC107),
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}

@Preview
@Composable
fun RatingPreview() {
    RatingBarView(rating = 2.5)
}

@Preview
@Composable
fun TenStarsRatingPreview() {
    RatingBarView(stars = 10, rating = 8.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    RatingBarView(rating = 5.0)
}

@Preview
@Composable
fun RatingPreviewWorst() {
    RatingBarView(rating = 1.0)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBarView(rating = 0.0, starsColor = Color.Gray)
}