package example.yorubaproverbs.presentation.dashboard.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ShowSearchView(
    searchText: String,
    searchLanguage: String,
    leadingIconClick: () -> Unit,
    cancelIconClick: () -> Unit,
    onValueChange: (String) -> Unit,
    toggleLanguageAction: (Int) -> Unit
) {
    Surface(elevation = 3.dp) {
        CustomSearchView(
            searchText,
            leadingIconClick,
            cancelIconClick,
            onValueChange
        )
        LanguageOption(searchLanguage) {
            toggleLanguageAction(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.CustomSearchView(
    searchQuery: String,
    leadingIconClick: () -> Unit,
    cancelIconClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 30.dp),
        value = searchQuery,
        onValueChange = {
            onValueChange(it)
        }
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 20.dp
            ),
            enabled = true,
            innerTextField = it,
            value = searchQuery,
            singleLine = true,
            interactionSource = remember { MutableInteractionSource() },
            visualTransformation = VisualTransformation.None,
            leadingIcon = {
                IconButton(
                    onClick = { leadingIconClick() },
                    content = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    },
                )
            }
        )
    }
    androidx.compose.material3.IconButton(
        modifier = Modifier.align(Alignment.CenterEnd),
        onClick = { cancelIconClick() },
        content = {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = Color.Gray
            )
        },
    )
}


@Composable
fun BoxScope.LanguageOption(searchLanguage: String, setLanguageOption: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.CenterEnd)
    ) {
        Button(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(end = 30.dp),
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = null
        ) {
            Text(searchLanguage)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            repeat(2) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        setLanguageOption(it)
                    }
                ) {
                    Text(
                        text = if (it == 1) "English" else "Yoruba",
                    )
                }
            }
        }
    }
}