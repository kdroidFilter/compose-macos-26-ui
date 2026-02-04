package io.github.kdroidfilter.darwinui.gallery.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import java.io.File

class GalleryProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()
        invoked = true

        val annotationName = "io.github.kdroidfilter.darwinui.gallery.GalleryExample"
        val symbols = resolver.getSymbolsWithAnnotation(annotationName)
            .filterIsInstance<KSFunctionDeclaration>()
            .toList()

        if (symbols.isEmpty()) return emptyList()

        val entries = mutableListOf<Pair<String, String>>() // constName -> sourceCode

        for (func in symbols) {
            val funcName = func.simpleName.asString()
            val filePath = func.containingFile?.filePath
            if (filePath == null) {
                logger.warn("Cannot find source file for $funcName")
                continue
            }

            val sourceText = File(filePath).readText()
            val body = extractFunctionBody(sourceText, funcName)
            if (body == null) {
                logger.warn("Cannot extract body for $funcName in $filePath")
                continue
            }

            entries.add(funcName to body)
        }

        if (entries.isEmpty()) return emptyList()

        // Sort entries alphabetically for stable output
        entries.sortBy { it.first }

        val code = buildString {
            appendLine("// GENERATED — do not edit")
            appendLine("package io.github.kdroidfilter.darwinui.sample.gallery.generated")
            appendLine()
            appendLine("object GallerySources {")
            for ((constName, body) in entries) {
                val escaped = escapeForTripleQuote(body)
                appendLine("    const val $constName: String = \"\"\"$escaped\"\"\"")
            }
            appendLine("}")
        }

        codeGenerator.createNewFile(
            dependencies = Dependencies(
                aggregating = true,
                sources = symbols.mapNotNull { it.containingFile }.toTypedArray(),
            ),
            packageName = "io.github.kdroidfilter.darwinui.sample.gallery.generated",
            fileName = "GallerySources",
        ).use { os ->
            os.write(code.toByteArray())
        }

        return emptyList()
    }

    /**
     * Extracts the body of a function (content between the outer { }) from source text.
     * Handles string literals (single-line and raw), comments, and nested braces.
     */
    internal fun extractFunctionBody(source: String, functionName: String): String? {
        // Find "fun <functionName>" — match word boundary
        val pattern = Regex("""fun\s+$functionName\s*\(""")
        val match = pattern.find(source) ?: return null

        // From the match, scan forward to find the opening brace of the function body
        var i = match.range.last + 1
        val len = source.length

        // Skip past the parameter list and any return type declaration to find '{'
        while (i < len && source[i] != '{') {
            i++
        }
        if (i >= len) return null

        // i is now at the opening '{' of the function body
        val bodyStart = i + 1
        var depth = 1
        i = bodyStart

        while (i < len && depth > 0) {
            val ch = source[i]
            when {
                // Raw string literal (triple-quoted)
                ch == '"' && i + 2 < len && source[i + 1] == '"' && source[i + 2] == '"' -> {
                    i += 3
                    // Skip until closing """
                    while (i + 2 < len) {
                        if (source[i] == '"' && source[i + 1] == '"' && source[i + 2] == '"') {
                            i += 3
                            break
                        }
                        i++
                    }
                    continue
                }
                // Regular string literal
                ch == '"' -> {
                    i++
                    while (i < len && source[i] != '"') {
                        if (source[i] == '\\') i++ // skip escaped char
                        i++
                    }
                    i++ // skip closing "
                    continue
                }
                // Character literal
                ch == '\'' -> {
                    i++
                    while (i < len && source[i] != '\'') {
                        if (source[i] == '\\') i++
                        i++
                    }
                    i++ // skip closing '
                    continue
                }
                // Line comment
                ch == '/' && i + 1 < len && source[i + 1] == '/' -> {
                    i += 2
                    while (i < len && source[i] != '\n') i++
                    continue
                }
                // Block comment
                ch == '/' && i + 1 < len && source[i + 1] == '*' -> {
                    i += 2
                    while (i + 1 < len) {
                        if (source[i] == '*' && source[i + 1] == '/') {
                            i += 2
                            break
                        }
                        i++
                    }
                    continue
                }
                ch == '{' -> depth++
                ch == '}' -> depth--
            }
            if (depth > 0) i++
        }

        if (depth != 0) return null

        // i is now at the closing '}'
        val bodyEnd = i
        val rawBody = source.substring(bodyStart, bodyEnd)
        return dedent(rawBody)
    }

    /**
     * Removes common leading whitespace from all non-blank lines.
     */
    private fun dedent(text: String): String {
        val lines = text.lines()

        // Drop leading/trailing blank lines
        val trimmedLines = lines.dropWhile { it.isBlank() }.dropLastWhile { it.isBlank() }
        if (trimmedLines.isEmpty()) return ""

        val minIndent = trimmedLines.filter { it.isNotBlank() }
            .minOfOrNull { line -> line.length - line.trimStart().length } ?: 0

        return trimmedLines.joinToString("\n") { line ->
            if (line.isBlank()) "" else line.drop(minIndent)
        }
    }

    /**
     * Escapes triple-quote sequences inside a string so it can be safely embedded in a
     * triple-quoted string literal. Replaces `"""` with `""\"""` (breaks the triple-quote
     * with a backslash escape spliced in).
     */
    private fun escapeForTripleQuote(text: String): String {
        // In Kotlin raw strings, $ needs escaping as ${'$'} for string templates
        // and """ needs to be broken up
        return text
            .replace("\$", "\${'$'}")
            .replace("\"\"\"", "\"\"" + "\${'\"'}")
    }
}
