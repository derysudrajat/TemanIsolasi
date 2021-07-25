package id.temanisolasi.utils

import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import id.temanisolasi.R

object Helpers {

    fun BottomAppBar.setRounded() {
        val background = this.background as MaterialShapeDrawable
        background.shapeAppearanceModel = background.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, 50f)
            .setTopLeftCorner(CornerFamily.ROUNDED, 50f)
            .build()
    }

    val dummyIconHome = listOf(
        R.drawable.ic_home,
        R.drawable.ic_guide,
        R.drawable.ic_explore,
        R.drawable.ic_person,
    )

    val dummyTemp = mutableListOf(
        38.9f, 38.2f, 37.6f, 37.9f, 36.7f, 37.2f, 36.3f,
        36.1f, 36.2f, 36.4f, 36.2f, 36.1f, 36.3f
    )
}