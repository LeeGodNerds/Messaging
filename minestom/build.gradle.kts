repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":core"))
    compileOnly("net.minestom.server:Minestom:1.0")
    testImplementation("net.minestom.server:Minestom:1.0")
}
