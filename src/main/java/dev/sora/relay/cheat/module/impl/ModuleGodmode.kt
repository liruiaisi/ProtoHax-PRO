package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.constants.Effect

class ModuleGodmode : CheatModule("GodMode","无敌模式") {
    private val modeValue = listValue("Mode", arrayOf("HYT","EC"), "HYT")
    private val amplifierValue = intValue("levels", 5, 1, 50)
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val packet = event.packet
        if(modeValue.toString()=="HYT") {
            if (packet is PlayerAuthInputPacket) {
                session.thePlayer.attackEntity(mc.thePlayer, EntityPlayerSP.SwingMode.NONE, false)
            }
        }
    }
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (modeValue.toString() == "HYT") {
            if (packet is AnimatePacket) {
                if (packet.action == AnimatePacket.Action.CRITICAL_HIT && packet.runtimeEntityId == mc.thePlayer.entityId) {
                    if (mc.thePlayer.tickExists % 5 == 0L) {
                        event.cancel()
                    }
                }
            }
        }
    }
    @Listen
    fun onTick(event: EventTick){
        if (event.session.thePlayer.tickExists % 20 != 0L) return
        event.session.netSession.inboundPacket(MobEffectPacket().apply {
            setEvent(MobEffectPacket.Event.ADD)
            runtimeEntityId = event.session.thePlayer.entityId
            effectId = Effect.INVISIBILITY
            amplifier = amplifierValue.get() - 1
            isParticles = false
            duration = 21 * 20
        })
        if (event.session.thePlayer.tickExists % 20 != 0L) return
        event.session.netSession.inboundPacket(MobEffectPacket().apply {
            setEvent(MobEffectPacket.Event.ADD)
            runtimeEntityId = event.session.thePlayer.entityId
            effectId = Effect.HEALTH_BOOST
            amplifier = amplifierValue.get() - 1
            isParticles = false
            duration = 21 * 20
        })
    }
    }
