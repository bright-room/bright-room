package net.brightroom.homepage.shared.lib

internal expect fun platformOpenUrl(url: String)

fun openUrl(url: String) {
    if (url.startsWith("https://") || url.startsWith("http://")) {
        platformOpenUrl(url)
    }
}
