plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	id ("org.sonarqube") version "3.5.0.2730"
	application
}

group = "ltr.org.capturenotes"
version = "0.0.1-SNAPSHOT"
repositories {
	mavenCentral()
	mavenLocal()
}

application {
	applicationDefaultJvmArgs = listOf(
		"-Dspring.configuration.uri=http://localhost:8083/configService/get/Dev/captureNotes",
		"-Dspring.profiles.active=dev",
		"-Dspring.help.url=http://localhost:8083/configService/get/Help/captureNotes"
	)
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.data:spring-data-envers:2.7.2")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("org.modelmapper:modelmapper:3.1.0")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("commons-io:commons-io:2.11.0")
	implementation("org.apache.commons:commons-csv:1.9.0")
	implementation("org.apache.poi:poi-ooxml:5.2.2")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation(files("libs/common-config-dev.jar"))
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}