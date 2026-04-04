package net.brightroom.homepage.shared.lib

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url) => { window.open(url, '_blank'); }")
private external fun wasmOpenUrl(url: String)

internal actual fun platformOpenUrl(url: String) {
    wasmOpenUrl(url)
}
