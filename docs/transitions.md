# Route Transitions
So far we have been using default transitions in our `Routes`. What if you want to something more fancy? `SafeRoute` also supports defining those custom transitions that provides core `Navigation Component` and `Accompanist` library

## Using Route Builders
We had already seen the default transitions in the previous chapter which is being used by `composable()` route builder.
In a similar fashion we can also use different transitions

### Dialog Transition
This transition will open you composable as classic pop-up dialog. Simply use the `dialog` route builder that uses `RouteSpec<*>` as a parameter
```kotlin
fun NavGraphBuilder.myGraph() {
    dialog(MyDialogRoute) {
        // Composable Content
    }
}
```

In case you want to modify the behaviour of your dialog, you can pass your custom `DialogProperties` 
```kotlin
fun NavGraphBuilder.myGraph() {
    dialog(
        MyDialogRoute
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.Inherit
        )
    ) {
        // Composable Content
    }
}
```




### Bottom Sheet

> Following transition is a part of the `Accompanist` Library and requires some additional setup for yor `NavHost`. Please refer for the [Accompanist Docs](https://google.github.io/accompanist/navigation-material/) for additional info.

Bottom sheet transitions is realized using `bottomSheet` route builder
```kotlin
fun NavGraphBuilder.myGraph() {
    
    bottomSheet(MyBottomSheetRoute) {
        // Composable Content
    }
}
```


### Animated Transition
> Following transition is a part of the `Accompanist` Library and requires some additional setup for yor `NavHost`. Please refer for the [Accompanist Docs](https://google.github.io/accompanist/navigation-animation/) for additional info.

Animated Transitions provided by `Accompanist` allow you to specify custom animations when navigating in and out of the screen.

To set it up you will have to use different `NavHost`. You can either use `AnimatedNavHost` from Accompanist or `SaferRouteAnimatedNavHost` provided by `SafeRoute`. 

Then you can specify your custom transitions as parameters

```kotlin
@Composable
fun MyApp() {
    SafeRouteAnimatedNavHost(HomeRoute) {
        composable(
            routeSpec = HomeRoute,
            enterTransition = fadeIn() + expandIn(),
            exitTransition = fadeOut() + shrinkOut(),
            popEnterTransition = slideIn(),
            popExitTransition = slideOut()
        ) {
            // Composable content
        }
    }
}
```

## Specifiyng Transition in Route Annotation
You can also specify your Route Transition inside the annotation. Based on that parameter, code-gen will create specific Route Buidlers based on the Route and the specified transition. Lets use our basic example

```kotlin
@Route()
@Composable
fun Home() { /** compose content **/ }
```
After build step is complete, besides Route Definition we've seen already, `SafeRoute` will aslo create a route builder for this screen specifically. 

Note that since this route dont have any parameters, we can invoke this route builder without providing `Home` composable

```kotlin
fun NavGraphBuilder.myGraph() {
    // short version
    home()

    // in case we have some params
    home { Home("value")}
}
```

`RouteBuilders` like this are already "transition aware". That means that when you change your transition in the annotation, it will also be changed in the `RouteBuilder` after codegen step.

### Dialog Transition
To use `dialog()` builder as your transition you can use `DialogRouteTransition` class. It provides `Default` implementation in case you don't need to specify any custom `DialogProperties`, or you can extend it for further cusomizations

```kotlin
// Default implementation
@Composable
@Route(
    name = "Home",
    transition = DialogRouteTransition.Default::class
)
fun Home() {}

// Custom implementation
@Composable
@Route(
    name = "Home2",
    transition = CustomDialogTransition::class
)
fun Home2() {}
```


### Bottom Sheet Transition

```kotlin
@Composable
@Route(
    name = "sheet",
    transition = BottomSheetRouteTransition::class,
)
fun Sheet() {}
```

### Animated Route Transition
`SafeRoute` provides abstract class called `AnimatedRouteTransition` that allows you to modify the animations. Similar to dialog transition there is also a default implementation thats using basic `fadeIn() and fadeOut()`

#### Default Animated Route Transition
```kotlin
@Route(transition = AnimatedRouteTransition.Default::class)
@Composable
fun Helllo() {}
```

#### Custom Animated Route Transition

```kotlin
@OptIn(ExperimentalAnimationApi::class)
object CustomAnimatedTransition : AnimatedRouteTransition() {
    override val enter: RouteEnterTransition = { expandIn() }
    override val exit: RouteExitTransition = { shrinkOut() }
    override val popEnter: RouteEnterTransition = enter
    override val popExit: RouteExitTransition = exit
}
```

And then use it inside the annotation

```kotlin
@Route(transition = CustomAnimatedTransition::class)
@Composable
fun Helllo() {}
```