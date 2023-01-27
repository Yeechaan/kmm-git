package io.test.kmmgit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform