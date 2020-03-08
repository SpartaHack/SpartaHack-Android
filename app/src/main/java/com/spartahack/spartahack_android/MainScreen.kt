package com.spartahack.spartahack_android

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/*
 * All of this code is copied from the link below. It handles the fragments and the view
 * pager.
 * https://pspdfkit.com/blog/2019/using-the-bottom-navigation-view-in-android/
 */

/**
 * Screens available for display in the main screen, with their respective titles,
 * icons, and menu item IDs and fragments.
 */
enum class MainScreen(@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    HOME(R.id.nav_home, R.drawable.ic_home_black_24dp, R.string.home_icon_title, HomeFragment(R.layout.home_view)),
    MAPS(R.id.nav_maps, R.drawable.ic_map_black_24dp, R.string.map_icon_title, MapsFragment(R.layout.maps_view)),
    FAQ(R.id.nav_faq, R.drawable.ic_forum_black_24dp, R.string.faq_icon_title, FAQFragment(R.layout.faq_view)),
    QR(R.id.nav_qr, R.drawable.ic_camera_alt_black_24dp, R.string.qr_icon_title, QRFragment(R.layout.qr_view)),
    PROFILE(R.id.nav_profile, R.drawable.ic_person_black_24dp, R.string.profile_icon_title, ProfileFragment(R.layout.profile_view))
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}
