package org.d3if3022.mobpro1.navigation

import org.d3if3022.mobpro1.ui.screen.KEY_ID_BARANG

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_BARANG}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}