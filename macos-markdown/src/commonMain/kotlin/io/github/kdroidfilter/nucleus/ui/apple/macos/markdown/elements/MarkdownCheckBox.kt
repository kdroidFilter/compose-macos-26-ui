package io.github.kdroidfilter.nucleus.ui.apple.macos.markdown.elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.mikepenz.markdown.compose.elements.MarkdownCheckBox
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CheckBox
import org.intellij.markdown.ast.ASTNode

@Composable
fun MarkdownCheckBox(
    content: String,
    node: ASTNode,
    style: TextStyle,
) = MarkdownCheckBox(
    content = content,
    node = node,
    style = style,
    checkedIndicator = { checked, modifier ->
        CheckBox(
            checked = checked,
            onCheckedChange = {},
            modifier = modifier,
        )
    },
)
