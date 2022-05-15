

![logo](assets/logo.svg)



# Introduction

A code generating library, inspired by SafeArgs for Android. Entrily based on the Jetpack Compose Navigation Component, SafeRoute uses its apis to ensure type-safety during navigation and allows you to define your navigation graphs without boilerplate code, hardcoded strings and more.

## Features

 - Removes code duplication when describing your routes and its arguments through out the application
 - Helper functions to declare your Routes using `RouteSpec` interface 
 - Support for Accompanist Animation/Material libraries 
 - Helper functions that will allow to obtain passed arguments easily, both in your Composables and ViewModel using `CompositionLocal` and `SavedStateHandle` respectivly
 - Safety during navigation: The arguments you declared are always passed and obtained in a type-safe way
 - Mandatory & Optional parameters

## Setup

in your project level `build.gradle`

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}	
}
```

And then in you app level `build.gradle`

```kotlin
dependencies { 
   
  // add either KAPT or KSP processor
  ksp("com.github.levinzonr.compose-safe-routing:processor-ksp:2.5.0")
  kapt("com.github.levinzonr.compose-safe-routing:processor-kapt:2.5.0")

  implementation("com.github.levinzonr.compose-safe-routing:core:2.5.0")
  // or in case you are using animation/material routes from accompanist
  implementation("com.github.levinzonr.compose-safe-routing:accompanist-navigation:2.5.0")
  
}
```



if you are using `KSP`, make sure you are also added KSP build folder

```gradle
applicationVariants.all { variant ->
    kotlin.sourceSets {
        getByName(variant.name) {
            kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
        }
    }
}
```

```gradle
applicationVariants.all {
    kotlin.sourceSets {
        getByName(name) {
            kotlin.srcDir("build/generated/ksp/$name/kotlin")
        }
    }
}
```


## Basic Usage

At the core of the `SafeRoute` there is a `@Route` annotation. This annotation is used to describe your composable destination. Here is the basic setup for the Profile Screen

```kotlin
@Route(name = "profile")
@Composable
fun ProfileScreen() {
    /** your screen */
}
```

Next you might want to add some arguments to your route. This is done using `@RouteArg` annotation. It takes a name and the type of the param. Here you can also specify whether or not the argument is optional. This will determine how argument will be attached to the path and if default value should be used. Note that due to Annotations  limitations the default value is passed as `String` and then casted to the type specifed.

```kotlin
@Composable
@Route("details", args = [
    RouteArg("id", String::class, isOptional = false),
    RouteArg("number", Int::class, isOptional = true, defaultValue = "1"),
]) 
fun DetailsScreen() {
  /** sweet composable code ** /
}
```

After you build the project, `SafeRoute` will generate multiple files based on the `@Route` annotations. These files now fully describe your Routes  and can be used  to describe you navigation graph easier and to navigate from one Route to another.

```kotlin
NavHost(startDestination = Routes.Profile.route) {
  composable(ProfieRoute) { 
  
     ProfileScreen(onShowDetails = {
       // navController extension used to navigate
       navController.navigateTo(DetailsRoute(id = "hello"))
     })
  }
  
   composable(DetailsRoute) {
      // get the arguments
      val args = LocalDetailsRouteArgs.current
      DetailsScreen(args)
   }
  

}
```

