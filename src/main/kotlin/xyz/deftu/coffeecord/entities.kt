package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.entities.message.Message
import xyz.deftu.coffeecord.entities.message.MessageBuilder
import xyz.deftu.coffeecord.entities.message.embed.*

fun message(builder: MessageBuilder.() -> Unit): Message = MessageBuilder()
    .apply(builder).build()
fun embed(builder: MessageEmbedBuilder.() -> Unit): MessageEmbed = MessageEmbedBuilder()
    .apply(builder).build()
fun embedFooter(builder: MessageEmbedFooterBuilder.() -> Unit): MessageEmbedFooter = MessageEmbedFooterBuilder()
    .apply(builder).build()
fun embedMedia(builder: MessageEmbedMediaBuilder.() -> Unit): MessageEmbedMedia = MessageEmbedMediaBuilder()
    .apply(builder).build()
fun embedAuthor(builder: MessageEmbedAuthorBuilder.() -> Unit): MessageEmbedAuthor = MessageEmbedAuthorBuilder()
    .apply(builder).build()
fun embedField(builder: MessageEmbedFieldBuilder.() -> Unit): MessageEmbedField = MessageEmbedFieldBuilder()
    .apply(builder).build()

class MessageEmbedFooterBuilder {
    var text: String? = null
    var icon: String? = null
    var proxyIcon: String? = null
    fun build(): MessageEmbedFooter =
        MessageEmbedFooter(text, icon, proxyIcon)
}

class MessageEmbedMediaBuilder {
    var url: String? = null
    var proxyUrl: String? = null
    var width = -1
    var height = -1
    fun build(): MessageEmbedMedia =
        MessageEmbedMedia(url, proxyUrl, width, height)
}

class MessageEmbedAuthorBuilder {
    var name: String? = null
    var url: String? = null
    var iconUrl: String? = null
    var proxyIconUrl: String? = null
    fun build(): MessageEmbedAuthor =
        MessageEmbedAuthor(name, url, iconUrl, proxyIconUrl)
}

class MessageEmbedFieldBuilder {
    var name: String? = null
    var value: String? = null
    var inline = false
    fun build(): MessageEmbedField =
        MessageEmbedField(name, value, inline)
}