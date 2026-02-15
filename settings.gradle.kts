pluginManagement {
    repositories {
        maven { url = uri("https://maven.myket.ir") }
        maven{ url = uri("https://en-mirror.ir") }

        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://maven.myket.ir") }
        maven{ url = uri("https://en-mirror.ir") }
        google()
        mavenCentral()
    }
}

rootProject.name = "Snax"
include(":sample")
include(":snax")
