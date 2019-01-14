plugins {
    `java-library`
    id("net.ltgt.apt") version ("0.20")
    id("net.ltgt.apt-idea") version ("0.20")
    id("net.ltgt.apt-eclipse") version ("0.20")
}

dependencies {
    implementation("yuriykulikov.rxplayer:entertainment-lib:1.0.1")

    annotationProcessor("org.immutables:value:2.7.4")
    compileOnly("org.immutables:value-annotations:2.7.4")
    compileOnly("org.immutables:builder:2.7.4")
    compileOnly("org.immutables:gson:2.7.4")

    implementation("io.vavr:vavr:0.9.3")
    implementation("io.vertx:vertx-core:3.6.2")
    implementation("io.vertx:vertx-web-client:3.6.2")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation("com.google.code.gson:gson:2.8.5")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.mockito:mockito-core:2.8.47")
}
