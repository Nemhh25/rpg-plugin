plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {

    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

// Local Paper development server. Keep this path here so deployment is one command.
val paperServerDir = layout.projectDirectory.dir("D:/Server-Test")

tasks.register<Copy>("deployToPaper") {
    group = "paper"
    description = "Builds the plugin and copies its JAR to the local Paper plugins folder."
    dependsOn(tasks.jar)

    from(tasks.jar.flatMap { it.archiveFile })
    into(paperServerDir.dir("plugins"))
}

tasks.register<Exec>("deployAndRunPaper") {
    group = "paper"
    description = "Deploys the plugin, then opens the local Paper server in a new window."
    dependsOn("deployToPaper")
    workingDir(paperServerDir.asFile)

    // 'start' detaches the server, so Gradle can finish while Paper keeps running.
    commandLine("cmd", "/c", "start", "Paper Dev Server", "start.bat")
}
