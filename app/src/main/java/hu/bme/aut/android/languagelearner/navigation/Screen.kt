package hu.bme.aut.android.languagelearner.navigation

sealed class Screen(val route: String) {
    object SetList: Screen("set_list")
    object SetDetail: Screen("set_detail")
    object Login: Screen("login/login")
    object Logout: Screen("login/logout")
    object Learning: Screen("learning")
    object Quiz: Screen("quiz")
}