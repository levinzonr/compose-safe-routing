# 2.5.0 Release Notes

## Disclaimer ⚠️
This update is quite big in terms of introducing new logic and deprecating the logic that was here since the beginning. Thus, I'd like to gather as much feedback as possible and encourage you to open issues in case you have any suggestions regarding newly introduced stuff. I'm also making this update as `beta` as there are things I would like to make it in full 2.5.0 and so there is somewhat of a breathing room in case of any feedback :)
Thanls!


## NavGraph Support
Version 2.5.0 introduces support to the Navigation Graphs so you can structure your routes in a more concise way. By default, all your current routes will be a part of the "Main" Graph - the default graph all route annotations have, and, since all NavGraphs need to have a starting point, you need to declare of your routes to be a "start", otherwise  the build may fail
```kotlin
@Route(
    transition = AnimatedRouteTransition.Default::class,
    navGraph = RouteNavGraph(start = true)
)
```
### Defining Custom Graphs
If you want to define your graph you can simply set another graph name and set a starting route for it

```kotlin
@Route(
    name = "PokemonList",
    transition = FadeInFadeOutTransition::class,
    navGraph = RouteNavGraph("pokedex", start = true)
)
```
This will generate a new file and called `PokedexGraph` and `PokedexGraphRoutes` which you can you for navigation or declaration, like so
```kotling
navigation(PokedexGraph) {
    pokemonList {
        // Composable content()
    }
}
and navigation
navController.navigateTo(PokedexGraph)
navContoller.navigateTo(PokemonDetailsRoute())

```

## New Navigation APIs and Deprecation of the Old Ones
`RoutesActions.kt` were deprecated in favor of the invoke operator that is a part of RouteSpec. We've added another navController extension `navigateTo()` that can be used together with it. This results in one less generated file and more straightforward usage. NavController+Routes.kt will also be deleted in the future releases

### 2.4.X - Old behaviour
```kotlin
navController.navigate(RouteActions.toDetails("id")
navController.navigateToDetails("id")
```
### 2.5.0 - New behavior
```kotlin
navController.navigateTo(DetailsRoute("Id"))
```

## Router API
2.5.0 Introduces new component called `Router`, which is basically a wrapper around your usual `navController`, but is specifically built to handle navigation using the newly introduced extensions. It is also an Interface, which means it would be easier to test your composables. `Router` can be accessed from Composition Local and used as so:
```kotlin
val router = LocalRouter.current
router.navigate(DetailsRoute("id"))
```


## Deprecation of Routes.kt
`Routes.kt` is now also deprecated since we've introduced the navigation graphs which allow you to structure in a similar fashion but in a more logical and flexible way. By Default, all your Routes will be a part of the `MainGraphRoutes.kt` so the migration should be fairly simple. Every new graph will hold its own set of Routes.

## KSP Support
From version 2.5.0 Safe Route now has the support for Kotlin Symbol Processing (KSP). The API is the same so the migration should be more or less painless (considering you already using 2.5.0-X version), however, the imports might change. Here is how to get started.

This is a pre-release based on the 2.5.0-beta01, make sure to also check whats new in beta01 release [here](https://github.com/levinzonr/compose-safe-routing/releases/tag/2.5.0-beta01)

> ⚠️ If you dont want to use KSP you can still use KAPT, KAPT support is not going anywhere :). However in order to keep the modules name aligned, `compiler` artifact was renamed into `processor-kapt`
```kotlin
    kapt("com.github.levinzonr.compose-safe-routing:processor-kapt:2.5.0-beta02")
```

### Add KSP Plugin (if you haven't already)
Gradle
```gradle
plugins {
    id  "com.google.devtools.ksp" version '"1.6.10-1.0.3" // 
}
```

Kotlin
```kotlin
plugins {
    id("com.google.devtools.ksp") version "1.6.10-1.0.3" // Depends on your kotlin version
}
```


# 2.4.0 Release Notes
## Introducing `RouteTransitions`

You can specify the desired transition using `route` builder.

```kotlin
route(Routes.Details, DefaultRouteTransition) { /* content */ }
```

You can also specify the desired transition right inside the `@Route` annotation like so. Doing so will allow you to use the generated route builder in your `NavHost` 

```kotlin
@Route(
    name = "HomeScreen",
    transition = AnimatedRouteTransition.Default::class
)

// then you can use the generated route builder
NavHost {
   homeScreen {
     val viewModel = hiltViewModel()
      HomeScreen(viewModel)
   }
}
```



## Misc

- `name` property of `@Route` annotation is now optional. By default it will take the name of the composable 
- Navigation Compose version upated to `2.4.1`

# 2.3.0 Release Notes

## New RouteArgs API

`RouteArgs` are now more easily accesseble using `CompositionLocal` APIs. Using that approach there is no need to pass you arguments down the tree as they will be accessible for you entire Route. Plus, its way more convinient than deailing it `NavBackStackEntry` directly. 

```kotlin
// OLD 2.2.X
composable(Routes.Details) { entry -> 
  val args = RouteDetailsArgsFactory.fromBackStackEntry(entry)
  DetailsScreen(args = args)
}

// NEW 2.3.0
composable(Routes.Details) {
  val args = LocalDetailsRouteArgs.current
  DetailsScreen(args = args)
}

// Or, retrieve it elsewhere :)
composable(Routes.Details) { DetailsScreen() }

DetailsScreen() {
  val args = LocalDetailsRouteArgs.current
  
  // also available through RouteSpec extension
  val args = Routes.Details.currentArgs
}
```

## DeepLinks Support

SafeRoute now can handle the DeepLinks for you. Simply provide the deep link param for the desired `Route` and thats it. Any parameters you expecting being passed through the deeplink will be handled by the RouteArgs API as well, if they have the same names

```kotlin
@Composable
@Route(
    name = "details",
    args = [ RouteArg("id", String::class, isOptional =false)],
    deepLinks = [RouteDeeplink("app://deeplink/{id}")]
)
fun DetailsScreen() {
  // deeplink args will aslo be here
  val args = LocalDetatailsRouteArgs.current
  
}
```



## Dialogs support

You can add use SafeRoute with dialogs.

```kotlin
fun NavGraphBuilder.mainGraph() {
  dialog(Routes.Popup) { Popup() }
  composable() { HomeScreen() }
}
```

## API changes

### *withArgs extensions

`composableWithArgs` and other `*withArgs` extensions have been deprecated in favor of `CompositionLocal `RouteArgs APIs



### New Annotations

New Annotations with the same name were created inside the `:core` module to facilitate future development and introduce new features. Old annotations are now deprecated and will still work, however, some new features might be missing 

#### Migration steps

1. Replace occurrences of `cz.levinzonr.saferoute.annotations.RouteArg` with `cz.levinzonr.saferoute.core.annotations.RouteArg`

2. Replace occurrences of `cz.levinzonr.saferoute.annotations.Route` with `cz.levinzonr.saferoute.core.annotations.Route`

3. Argument types are now specified using KClass<*>, list of supported types has not changed

   

# 2.2.0 Release Notes

## NavController Extensions

`RouteActions` are now available through the extensions of the navController. 

```kotlin
// 2.1.X
navController.navigate(RouteActions.toDetails("myId"))

// 2.2.0
navController.navigateToDetails("myId")
```



## Dependencies Updates

- Update Navigation Compose to 2.4.0-alpha10
- Update Accompanist to 0.20.0

# 2.1.0 Release Notes

# SafeRoute + Accompanist = :heart:

SafeRoute now have a new artifact that supports building your Navigation Graph using Accompanist Navigation Compose and Navigation Material libraries. If you are using (or planning) to use Animations/Material  using Jetpack Compose Navigation, you just need to add this new artifact along side you Accompanist dependencies.

> No need to have both `:core` and `:accompanist-navigation` . If you were using :core artifact previously and want use accompanist one, you can just replace `:core`

```
implementation("com.github.levinzonr.compose-safe-routing:accompanist-navigation:2.1.0")
```



### SafeRoute + Animation

- Check [Accompanist documentation](https://google.github.io/accompanist/navigation-animation/) for initial setup

- Use `cz.levinzonr.saferoute.accompanist.navigation.composableWithArgs` or `cz.levinzonr.saferoute.accompanist.navigation.composable` builder extensions instead of `com.google.accompanist.navigation.animation.composable`

- Profit:

  ```kotlin
  composableWithArgs(Routes.Profile) { _, _ ->
      ProfileScreen {
          controller.navigate(RoutesActions.toDetails("ID", 0))
      }
  }
  ```



### SafeRoute + Material Navigation

- Check [Accompanist doccumentation](https://google.github.io/accompanist/navigation-material/) for intial setup

- Use `import cz.levinzonr.saferoute.accompanist.navigation.bottomSheet` or `import cz.levinzonr.saferoute.accompanist.navigation.bottomSheetWithArgs` instead of `import com.google.accompanist.navigation.material.bottomSheet` 

- Profit: 

  ```Kotlin
  bottomSheetWithArgs(Routes.Details) { _, args ->
      DetailsScreen(args = args, hiltViewModel())
  }
  bottomSheetWithArgs(Routes.Home) {
      HomeScreen(hiltViewModel())
  }
  ```

# 2.0.0 Release Notes

## NavGraphBuilder Extensions

The library now provides multiple extension function that you can use to declare your Routes

1.0.X

```kotlin
composable(Routes.screen.path, RouteSpec.screen.navArgs) { entry ->
   val args = ScreenArgs.fromBackStackEntry(it)
}
```

2.0.0

```kotlin
composable(Routes.Screen) {
   val args = ScreenArgsFactory.fromBackStackEntry(it)
}

// or, in case you dont want to handle arguments manually
composableWithArgs(Routes.Screen) { _, args
   // you screen             
}
```


Minor changes

- Custom `NavHost()`  that can accept the RouteSpec
- `navigation()` builder

## Breaking Changes

## Imports 

Annotations were moved to the different package. This can be fixed quicky by using find and replace  from `cz.levinzonr.router.core`  to `cz.levinzonr.saferoute.annotations`

1.0.X

```
import cz.levinzonr.router.core.Route
import cz.levinzonr.router.core.RouteArg
import cz.levinzonr.router.core.RouteArgType
```



2.0.0

```
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.annotations.RouteArg
import cz.levinzonr.saferoute.annotations.RouteArgType
```



### Retrieveing Arguments

Static functions of Args were replaced with arguments were replaced with ArgsFactories. `fromBackStackEntry()` is now an extension function

1.0.X

```
SomeRouteArgs.fromSavedStatedHandle(savedStateHandle)
SomeRouteArgs.fromBackStackEntry(entry)
```

2.0.0

```
SomeRouteArgsFactory.fromSavedStateHandle(savedStateHandle)
SomeRouteArgsFactory.fromBundle(bundle)
SomeRouteArgsFactory.fromBackStackEntry(entry) -- an extension, using fromBundle() under the hood
```





### Generated Files

- `RouteSpec.path` renamed to `RouteSpec.route`

- `RouteSpec.route` now is always capitalised