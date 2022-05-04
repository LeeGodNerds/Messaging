repositories {
    maven("https://repo.velocitypowered.com/snapshots/")
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":core"))
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
}