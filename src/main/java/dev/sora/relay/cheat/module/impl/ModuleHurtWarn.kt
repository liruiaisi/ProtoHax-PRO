package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.Listen

class ModuleHurtWarn : CheatModule("HurtWarn","伤害提醒") {

    private val messageValue = boolValue("Message", true)
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (event.packet is EntityEventPacket) {
            if (event.packet.type== EntityEventType.HURT && event.packet.runtimeEntityId==mc.thePlayer.entityId){

                if(messageValue.get())chat("Hurt!")
            }
        }
    }
}