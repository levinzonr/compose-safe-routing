# Defining Routes

Defining your Routes is basic functionality provided by SafeRoute. Every generated Route implements `RouteSpec` interface, which contains everything `Navigation Component` needs to know in order to include as part of navigation graphs. 

```kotlin
interface RouteSpec<A> {
    /**
     * Fully qualified path of the Route that will be used for the navigation
     * In case Route will have arguments specified, this value will also contain arguments in its path
     */
    val route: String

    /**
     * List of the NavArgs that this Route accepts
     */
    val navArgs: List<NamedNavArgument>

    /**
     * Helper class that serves as Arguments retriever. Use this class in order to
     * obtainy your arguments either from the SavedStateHandle in you ViewModel or NavBackStackEntty
     */
    val argsFactory: RouteArgsFactory<A>

    /**
     * List of the deeplink served by this Route
     */
    val deepLinks: List<NavDeepLink>
}
```

To generate simple `RouteSpec` for your Route simply annotate it using `@Route` annotation

```kotlin
@Route
@Composable
fun Home() {
    /* UI */
}
```

In this example `SafeRoute` will generate the most basic RouteSpec with the implicit name (taken from the Name of the composable function) and a `Route` postfix, resulting in `HomeRoute`. From this point you can start adding your Route in you Navigation Graph. To do so, `SafeRoute` provides number of NavGraphBuilder extensions, with the names you might be familiar. They are the same as the ones provided by Jetpack Navigation Component artifact, with the only difference: Instead of passing a bunch of details to it, you can simply pass your `RouteSpec`

> There multiple builders available, learn more in our `RouteTransitions` doc

```kotlin
val navController = rememberNavController()
NavHost(startDestination = HomeRoute.route) {
    composable(HomeRoute) {
        Home()
    }
}
```

In case you want provide a different name for your route you can simply pass the `name` parameter as parth of the annotation
```kotlin
@Route(name = "profile")
@Composable
fun ProfileScreen() {
     /* UI */
}
```

And lets our new route to the `NavGraph`

```kotlin
val navController = rememberNavController()
NavHost(startDestination = HomeRoute.route) {
    composable(HomeRoute) {
        Home()
    }
    composable(ProfileRoute) {
        ProfileScreen()
    }
}
```


## Navigation

Now that you have multiple Routes in your navigation graph lets explore how you can navigate from one to another. In Jetpack Compose, all destinations reresented as Strings. Using `SafeRoute` you can take advantage of the `Direction`. `Direction` represents a path that Navigation Component can use to navigate to your route, including every argument (if any) already serialized into string. Accessing `Direction` is straight forward, all you need to do is `invoke()` your Route

```kotlin
navController.navigate(ProfileRoute().route)
```

`SafeRoute` also provides `navigateTo`  extension that you can use to make it a bit shorter
```kotlin
navController.navigateTo(ProfileRoute())
```
