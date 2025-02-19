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
//    register<Exec>("linkSsdk"){
//        val ssdkSource = "../smithy/build/smithyprojections/smithy/source/typescript-ssdk-codegen"
//        val ssdkDest = "ssdk"
//        commandLine("ln", "-fs", ssdkSource, ssdkDest)
//        dependsOn(":smithy:smithyBuild")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("ssdkYarnInstall") {
//        workingDir.set(file("ssdk"))
//        args.set(listOf("install"))
//        dependsOn("linkSsdk")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("ssdkYarnBuild") {
//        workingDir.set(file("ssdk"))
//        args.set(listOf("build"))
//        dependsOn("ssdkYarnInstall")
//    }
//
//    register("buildSSDK") {
//        dependsOn("ssdkYarnBuild")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("serverYarnInstall") {
//        workingDir.set(projectDir)
//        args.set(listOf("install"))
//        dependsOn("buildSSDK")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("serverYarnBuild") {
//        workingDir.set(projectDir)
//        args.set(listOf("build"))
//        dependsOn("serverYarnInstall")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("build") {
//        dependsOn("serverYarnBuild")
//    }
//
//    register<com.github.gradle.node.yarn.task.YarnTask>("run") {
//        workingDir.set(projectDir)
//        args.set(listOf("start"))
//        dependsOn("build")
//    }
//
//}
//
//
