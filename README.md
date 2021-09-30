[![](https://jitpack.io/v/levinzonr/compose-safe-routing.svg)](https://jitpack.io/#levinzonr/compose-safe-routing)


[Blogpost](https://engineering.monstar-lab.com/2021/08/30/Safe-Navigation-With-Jetpack-Compose)

# Compose Safe Routing

A small code generating library, inspired by SafeArgs for android, that generates helper code that can be used for Jetpack Compose Navigation Component.

## Release Notes
 - [Version 2.1.0](RELEASE_NOTES.md/#210-release-notes)
 - [Version 2.0.0](RELEASE_NOTES.md/#200-release-notes)

## Features

 - Removes code duplication when describing your routes and its arguments through out the application
 - Helper functions to declare your Routes using `RouteSpec` interface 
 - Support for Accompanist Animation/Material libraries 
 - Helper functions that will allow to obtain passed arguments easily
 - Safety during navigation: `RoutesActions.kt` always contains arguments your destination needs
 - Mandatory & Optional parameters

## Installation

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
    kapt("com.github.levinzonr.compose-safe-routing:compiler:2.1.0")
  
  implementation("com.github.levinzonr.compose-safe-routing:core:2.1.0")
  // or in case you are using animation/material routes from accompanist
  implementation("com.github.levinzonr.compose-safe-routing:accompanist-navigation:2.1.0")
  
}
```

```groovy
dependencies {
    kapt 'com.github.levinzonr.compose-safe-routing:compiler:2.1.0'
  
  implementation 'com.github.levinzonr.compose-safe-routing:core:2.1.0'
  // or in case you are using animation/material routes from accompanist
  implementation 'com.github.levinzonr.compose-safe-routing:accompanist-navigation:2.1.0'
}

```

## Description
**Safe routing** serves as a `safeArgs` analogue for jetpack compose navigation library. Using annotation processor
it generates possible possible Paths And Actions so you don't have to hardcode argument names and names of the routes.

### @Route
First apply `@Route` annotation on the composable that represent a screen in you application
```kotlin
@Route(name = "profile")
@Composable
fun ProfileScreen() {
    /** your screen */
}
```

### @RouteArg
Its also possible describe the arguments for your routes using `@RouteArg` annotation. It takes a name and the type of the param. 

> Currently supported types: String, Int, Float, Long, Boolean

You can also specify whether or not the argument is optional or not. This will determine how argument will be attached to the path and if default value should be used. Note that due to Annotations  limitations the default value is passed as String and then casted to the type specifed.



```kotlin
@Composable
@Route("details", args = [
    RouteArg("id", RouteArgType.StringType, false),
    RouteArg("number", RouteArgType.IntType, true, defaultValue = "1"),
]) 
fun DetailsScreen() {
  /** sweet composable code ** /
}
```

### Output

After you build your project with these annotations applied several files will be generated for you. First one is `Routes`, in which you can access all routes with their corresponding paths and arguments
Another one is `RouteActions` where you can build these paths as a valid destination with all arguments applied. With the examples above this file would look like this

Additionally, an Argument wrapper would be generated for each route, so you can easily access it from either `NavBackStackEntry` or from `SavedStateHandle` in your `ViewModel`



This will allow you to declare your composable inside `NavHost` more easilly by using `NavGraphBuilder` extensions like so

```kotlin
NavHost(startDestination = Routes.Profile.route) {
  composable(Routes.Profie) { 
     ProfileScreen()
  }
  
   composableWithArgs(Routes.Details) { entry, args -> 
      DetailsScreen(args)
   }
  
  // or in case you want to process args manually 
  composable(Routes.Details) { entry -> 
      DetailsScreen(DetailsRouteArgsFactory.fromBackStackEntry(entry))
  }
}
```





**Routes.kt**

```kotlin
object Routes {
  val profile: RouteSpec = object : RouteSpec {
    override val route: String = "profile"
    override val navArgs: List<NamedNavArgument> = listOf()
    override val argsFactory: RouteArgsFactory<ProfileRouteArgs> = EmptyArgsFactory
  }
  
  val details: RouteSpec = object : RouteSpec {
    override val route: String = "details/{id}?number={number}"
    override val navArgs: List<NamedNavArgument> = DetailsRouteArgs.navArgs
    override val argsFactory: RouteArgsFactory<ProfileRouteArgs> = DetailsRouteArgsFactory

  }
```




**RoutesActions.kt**
```kotlin
object RoutesActions {
  fun toProfile(): String = "profile"
	fun toDetails(id: String, number: Int = 1): String = "details/$id?number=$number"
  fun toDetails(id: String): String = "details/$id"}
```

**Details Route Args**

```kotlin
data class DetailsRouteArgs(
  val id: String,
  val number: Int
) {
  companion object {
    /**
     * NamedNavArgs representation for DetailsRouteArgs
     */
    val navArgs: List<NamedNavArgument> = listOf(
      navArgument("id") {
        type = NavType.StringType 
        nullable = false
      },

      navArgument("number") {
        type = NavType.IntType 
        nullable = false
        defaultValue = 1
      },

    )
  }
}
```

