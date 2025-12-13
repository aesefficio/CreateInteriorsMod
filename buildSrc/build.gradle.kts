@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
	mavenCentral()
	maven("https://maven.neoforged.net/releases") {
		content {
			includeGroupAndSubgroups("net.neoforged")
		}
	}
	maven("https://maven.fabricmc.net/") {
		content {
			includeGroupAndSubgroups("net.fabricmc")
			includeGroup("fabric-loom")
		}
	}

	maven("https://maven.kikugie.dev/releases") {
		content {
			includeGroupAndSubgroups("dev.kikugie")
		}
	}

	maven("https://maven.wagyourtail.xyz/snapshots") {
		content {
			includeGroupAndSubgroups("xyz.wagyourtail")
		}
	}

	maven("https://maven.wagyourtail.xyz/releases") {
		content {
			includeGroupAndSubgroups("xyz.wagyourtail")
		}
	}
}

kotlin {
	jvmToolchain(21)
	compilerOptions.languageVersion = KotlinVersion.KOTLIN_2_2
	compilerOptions.freeCompilerArgs.addAll(
		"-Xcontext-parameters"
	)
}

dependencies {
	implementation("xyz.wagyourtail:manifold-gradle:1.1.1-SNAPSHOT")
	implementation("net.neoforged:moddev-gradle:2.0.107")
	implementation("net.fabricmc:fabric-loom:1.11.8")
	implementation("net.fabricmc:tiny-remapper:0.11.1")
}