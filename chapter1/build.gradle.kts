plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.parent!!.ext["kotlin_version"]}")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation("net.wuerl.kotlin:assertj-core-kotlin:0.1.1")
    implementation("junit:junit:4.12")
    implementation("org.mockito:mockito-core:2.8.47")
}
