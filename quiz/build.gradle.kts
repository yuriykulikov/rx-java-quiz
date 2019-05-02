plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm")
    id("net.ltgt.apt") version ("0.20")
    id("net.ltgt.apt-idea") version ("0.20")
    id("net.ltgt.apt-eclipse") version ("0.20")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.parent!!.ext["kotlin_version"]}")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation("net.wuerl.kotlin:assertj-core-kotlin:0.1.1")
    implementation("junit:junit:4.12")
    implementation("org.mockito:mockito-core:2.8.47")
    implementation("io.vavr:vavr:0.9.3")
    annotationProcessor("org.immutables:value:2.7.4")
    compileOnly("org.immutables:value-annotations:2.7.4")
    testCompile("org.immutables:value-annotations:2.7.4")
    testAnnotationProcessor("org.immutables:value:2.7.4")
}
