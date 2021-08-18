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