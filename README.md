[![](https://jitpack.io/v/levinzonr/compose-safe-routing.svg)](https://jitpack.io/#levinzonr/compose-safe-routing)

# Compose Safe Routing

## Installation

```kotlin
   kapt("com.github.levinzonr.compose-safe-routing:compiler:0.3.0")
implementation("com.github.levinzonr.compose-safe-routing:core:0.3.0")
```

```groovy
    dependencies {
    kapt 'com.github.levinzonr.compose-safe-routing:compiler:0.3.0'
    implementation 'com.github.levinzonr.compose-safe-routing:core:0.3.0'
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
Its also possible describe the arguments for your routes using `@RouteArg` annotation. It takes a name and the type of the param

> Currently Supported Arg types: String, Int, Float, Boolean, Long

```kotlin
@Route(name = "details", arguments = [@RouteArg("id", String::class)])
@Composable
fun DetailsScreen() {
    /** your screen */
}
```

### Output
After you build your project with these annotations applied several files will be generated for you. First one is `Routes`, in which you can access all paths available.
Another one is `RouteActions` where you can build these paths as a valid destination with all arguments applied