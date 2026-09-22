package com.example.varsivo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.varsivo.ui.theme.VarsivoSearchBar
import com.example.varsivo.ui.theme.VarsivoTextSecondary

/**
 * A real, typeable search field (previously this was a static decorative
 * row that looked like a search bar but couldn't accept input — that was
 * the "search doesn't work" bug). [value]/[onValueChange] wire it up to
 * whatever list the screen wants filtered.
 *
 * [onSearchSubmit], if set, fires only when the person presses the
 * keyboard's "search" action — not just from tapping the field. (An
 * earlier version navigated away as soon as you tapped in on Home, which
 * was confusing — typing should always just type.)
 */
@Composable
fun VarsivoSearchBar(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "Search",
    onSearchSubmit: (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, color = VarsivoTextSecondary) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = VarsivoTextSecondary)
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = VarsivoTextSecondary)
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        keyboardOptions = if (onSearchSubmit != null) {
            KeyboardOptions(imeAction = ImeAction.Search)
        } else {
            KeyboardOptions.Default
        },
        keyboardActions = KeyboardActions(
            onSearch = { onSearchSubmit?.invoke() }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = VarsivoSearchBar,
            unfocusedContainerColor = VarsivoSearchBar,
            disabledContainerColor = VarsivoSearchBar,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
