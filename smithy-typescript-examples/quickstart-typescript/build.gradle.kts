
// Add repositories for all subprojects to resolve dependencies.
allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}
// Add gradle tasks
// Custom clean task to remove all build artifacts and dependencies
tasks.register<Delete>("clean") {
    delete(
        "server/node_modules",
        "server/dist",
        "server/ssdk",
        "client/node_modules",
        "client/sdk",
    )
}



