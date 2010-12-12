package goog.editor.plugins

class AbstractBubblePlugin extends goog.editor.Plugin
class AbstractDialogPlugin extends goog.editor.Plugin

class EnterHandler extends goog.editor.Plugin

class BasicTextFormatter extends goog.editor.Plugin {
	def execCommandHelper_ (command:String, value:String = "", preserveDir:Boolean = false, styleWithCss:Boolean = false) {}
}

class HeaderFormatter extends goog.editor.Plugin
class TableEditor extends goog.editor.Plugin
class RemoveFormatting extends goog.editor.Plugin
class Blockquote (requiresClassNameToSplit:Boolean) extends goog.editor.Plugin

class LinkBubble extends AbstractBubblePlugin
class LinkDialogPlugin extends AbstractDialogPlugin
class TagOnEnterHandler (tag:String) extends EnterHandler
