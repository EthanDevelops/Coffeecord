package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.entities.message.embed.*
import java.time.OffsetDateTime

fun embed(builder: MessageEmbedBuilderBuilder.() -> Unit): MessageEmbed = MessageEmbedBuilderBuilder()
    .apply(builder).build().build()

class MessageEmbedBuilderBuilder {
    private val builder = MessageEmbedBuilder()

    var title: String? = null
    var description: String? = null
    var url: String? = null
    var timestamp: OffsetDateTime? = null
    var colour = 0
    var fields: List<MessageEmbedField> = mutableListOf()

    fun footer(builder: MessageEmbedFooterBuilder.() -> Unit) {
        this.builder.footer = MessageEmbedFooterBuilder()
            .apply(builder).build()
    }
    fun image(builder: MessageEmbedMediaBuilder.() -> Unit) {
        this.builder.image = MessageEmbedMediaBuilder()
            .apply(builder).build()
    }
    fun thumbnail(builder: MessageEmbedMediaBuilder.() -> Unit) {
        this.builder.thumbnail = MessageEmbedMediaBuilder()
            .apply(builder).build()
    }
    fun video(builder: MessageEmbedMediaBuilder.() -> Unit) {
        this.builder.video = MessageEmbedMediaBuilder()
            .apply(builder).build()
    }
    fun author(builder: MessageEmbedAuthorBuilder.() -> Unit) {
        this.builder.author = MessageEmbedAuthorBuilder()
            .apply(builder).build()
    }
    fun field(builder: MessageEmbedFieldBuilder.() -> Unit): MessageEmbedField =
        MessageEmbedFieldBuilder()
            .apply(builder).build()

    fun build(): MessageEmbedBuilder {
        builder.fields = fields
        return builder
    }
}

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