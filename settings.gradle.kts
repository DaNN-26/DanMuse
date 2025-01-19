pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-android/")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-android/")
    }
}

rootProject.name = "DanMuse"
include(":app")
include(":media")
include(":mvi")
include(":util")
include(":network")
