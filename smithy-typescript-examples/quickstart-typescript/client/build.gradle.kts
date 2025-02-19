//plugins {
//    id("com.github.node-gradle.node") version "3.5.1"
//}
//
//// Node configuration
//node {
//    version.set("20.11.0")
//    download.set(true)
//}
//
//// Tasks
//tasks {
//    register<Exec>("linkSdk"){
//        val sdkSource = "../smithy/build/smithyprojections/smithy/source/typescript-client-codegen"
//        val sdkDest = "sdk"
//        commandLine("ln", "-fs", sdkSource, sdkDest)
//        dependsOn(":smithy:smithyBuild")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("sdkYarnInstall") {
//        workingDir.set(file("sdk"))
//        args.set(listOf("install"))
//        dependsOn("linkSdk")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("sdkYarnBuild") {
//        workingDir.set(file("sdk"))
//        args.set(listOf("build"))
//        dependsOn("sdkYarnInstall")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("clientYarnInstall") {
//        workingDir.set(projectDir)
//        args.set(listOf("install"))
//        dependsOn("sdkYarnBuild")
//    }
//
//    register("build"){
//        dependsOn("clientYarnInstall")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("run") {
//        workingDir.set(projectDir)
//        args.set(listOf("start"))
//        dependsOn("build")
//    }
//}
//
