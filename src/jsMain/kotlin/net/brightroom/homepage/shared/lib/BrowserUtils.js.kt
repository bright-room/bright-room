package net.brightroom.homepage.shared.lib

import kotlinx.browser.window

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}
