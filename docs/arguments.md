# Defining And Obtaining Arguments
This page will walk you through defining arguments for your Routes, passing and obtaining them once the navigation is done.

## Defining Arguments
Arguments are defined using `@RouteArg` annotations. The annotation allows you to sepcify the argument's name, type and wether it supposed to be optional or or not. Here how the annotation looks like
```kotlin
annotation class RouteArg(
    val name: String,
    val type: KClass<*> = String::class,
    val isOptional: Boolean = false,
    val isNullable: Boolean = false,
    val defaultValue: String = AnnotationsDefaults.NULL
)
```

### Mandatory Arguments
By default every described by the annotation is considered **not nullable** and **not optional**. The example bellow will generate a Route with one argument called "id" of a type String
```kotlin
    @Composable
    @Route(
        args = [RouteArgs(name = "id", type = String::class)]
    )
    fun DetailsExample() {}
```

### Optional Arguments
Optional arguments can be defined using `isOptional` flag. When set to true, it is also **required** to provide the default value using the `defaultValue` parameter.

Note, Due to the limitatoins of the Annotations classes, the default value must be `String`. Later on, it will be casted back to the apporpriate type.
```kotlin
    @Composable
    @Route(
        args = [
            RouteArgs(
                name = "index",
                type = Int::class,
                isOptional = true,
                defaultValue = 1.toString()
            )
            ]
    )
    fun IndexExample() {}
```

## Navigation With Arguments
The navigation with arugments is done in a similar fasion as it is without it. After the arugments are defined for the route, and build process is complete, the `invoke()` operator for the routes with arguments will change. 

Thus, when you change the argument name/type, you will get the compile time error every time you forget to update the navigation code, ensuring the arguments are passed all the time

```kotlin
val navController = rememberNavController()

// mandatory id
navController.navigateTo(DetailsRoute("myId"))

// optional index
navController.navigateTo(IndexRoute())
navController.navigateTo(IndexRoute(index = 1))

```

## Obtaining Arguments
Now that we've seen how to define and pass the arguments for your route, let's see how to actually obtain the value.

`SafeRoute` provides multiple ways you can access the arguments. It is achieved using `RouteArgsFactory` component. 

```kotlin
interface RouteArgsFactory<T> {
    fun fromBundle(bundle: Bundle?): T
    fun fromSavedStateHandle(handle: SavedStateHandle?): T
    val LocalArgs: ProvidableCompositionLocal<T>
}
```

Every `RouteSpec` has its own `RouteArgsFactory` implementation. It allows you to grab your arguments either from `NavBackStackEntry` 

```kotlin
composable(IndexRoute) { entry -> 
    val args = IndexRouteArgsFactory.fromBackStackEntry(entry)
    IndexScreen(args)
}
```

or `SavedStateHandle` that is injected in your `ViewModel`

```kotlin
class DetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val args = DetailsRouteArgsFactory.from(savedStateHandle)
}
```

### Composition Local
 Additionaly, for every time you use `NavGraphBuilders` from `SafeRoute`, the arguments are saved in the `CompositionLocal` and can be accessed at any point inside your UI tree.



```kotlin
composable(IndexRoute) {
    val args = LocalIndexArgs.current
    IndexScreen(args)
}
// or
@Composable
fun IndexScreen() {
    val args = LocalIndexArgs.current
}

```