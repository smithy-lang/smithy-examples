
// Add repositories for all subprojects to resolve dependencies.
allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks{
    register("clean"){
        exec{
            commandLine("bash", "-c", "rm -rf */build */dist */node_modules client/sdk server/ssdk")
        }
    }
}