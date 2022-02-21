
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