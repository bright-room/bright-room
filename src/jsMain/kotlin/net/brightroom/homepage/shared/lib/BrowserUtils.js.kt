package net.brightroom.homepage.shared.lib

import kotlinx.browser.window

internal actual fun platformOpenUrl(url: String) {
    window.open(url, "_blank")
}
