package cz.levinzonr.saferoute.screens.statssheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import cz.levinzonr.saferoute.R
import cz.levinzonr.saferoute.accompanist.navigation.transitions.BottomSheetRouteTransition
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg
import cz.levinzonr.saferoute.screens.statssheet.args.LocalPokemonStatsRouteArgs

@ExperimentalMaterialNavigationApi
@Composable
@Route(
    name = "PokemonStats",
    transition = BottomSheetRouteTransition::class,
    args = [
        RouteArg("name", type = String::class),
        RouteArg("category", type = String::class, isNullable = true),
        RouteArg("hp", type = Int::class),
        RouteArg("imageRes", type = Int::class, isOptional = true, defaultValue = R.drawable.poke001.toString())
    ]
)
fun PokemonStatsSheet() {
   val args = LocalPokemonStatsRouteArgs.current
   Column(
       modifier = Modifier.padding(64.dp).fillMaxWidth(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(16.dp)
   ) {
       Image(painter = painterResource(id = args.imageRes), contentDescription = "")
       Text(text = args.name, style = MaterialTheme.typography.h6)
       Text(text = "Category: ${args.category}")
       Text(text = "HP: ${args.hp}")
   } 
}