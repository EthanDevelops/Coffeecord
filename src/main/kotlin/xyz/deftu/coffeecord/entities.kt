package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.entities.message.Message
import xyz.deftu.coffeecord.entities.message.MessageBuilder

fun message(builder: MessageBuilder.() -> Unit): Message = MessageBuilder()
    .apply(builder).build()