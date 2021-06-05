[![](https://jitpack.io/v/levinzonr/compose-safe-routing.svg)](https://jitpack.io/#levinzonr/compose-safe-routing)

# Compose Safe Routing

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
    kapt("com.github.levinzonr.compose-safe-routing:compiler:1.0.0")
    implementation("com.github.levinzonr.compose-safe-routing:core:1.0.0")
}
```

```groovy
dependencies {
    kapt 'com.github.levinzonr.compose-safe-routing:compiler:1.0.0'
    implementation 'com.github.levinzonr.compose-safe-routing:core:1.0.0'
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

**Routes.kt**

```kotlin
object Routes {
  val profile: RouteSpec = object : RouteSpec {
    override val path: String = "profile"
    override val navArgs: List<NamedNavArgument> = listOf()
  }
  
  val details: RouteSpec = object : RouteSpec {
    override val path: String = "details/{id}?number={number}"
    override val navArgs: List<NamedNavArgument> = DetailsRouteArgs.navArgs
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
    /**
     * A Helper function to obtain an instance of DetailsRouteArgs from NavBackStackEntry
     */
    fun fromNavBackStackEntry(args: NavBackStackEntry): DetailsRouteArgs {
      val id = requireNotNull(args.arguments?.getString("id"))
      val number = requireNotNull(args.arguments?.getInt("number"))
      return DetailsRouteArgs(id, number)
    }

    /**
     * A Helper function to obtain an instance of DetailsRouteArgs from SavedStateHandle
     */
    fun fromSavedStatedHandle(args: SavedStateHandle): DetailsRouteArgs {
      val id = requireNotNull(args.get<String>("id"))
      val number = requireNotNull(args.get<Int>("number"))
      return DetailsRouteArgs(id, number)
    }
  }
}
```

