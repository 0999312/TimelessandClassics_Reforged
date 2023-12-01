package com.tac.guns.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.event.BeforeCameraSetupEvent;
import com.tac.guns.client.event.BeforeRenderHandEvent;
import com.tac.guns.client.handler.AimingHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @Inject(method = "getFov", at = @At("HEAD"))
    public void getIsRenderHand(Camera p_109142_, float p_109143_, boolean p_109144_, CallbackInfoReturnable<Double> cir){
        AimingHandler.get().isRenderingHand = !p_109144_;
    }

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    public void beforeHandRender(PoseStack p_109121_, Camera p_109122_, float p_109123_, CallbackInfo ci){
        MinecraftForge.EVENT_BUS.post(new BeforeRenderHandEvent(p_109121_));
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;onCameraSetup(Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/Camera;F)Lnet/minecraftforge/client/event/EntityViewRenderEvent$CameraSetup;"))
    public void beforeCameraSetup(float p_109090_, long p_109091_, PoseStack p_109092_, CallbackInfo ci){
        BeforeCameraSetupEvent event = new BeforeCameraSetupEvent(p_109090_);
        MinecraftForge.EVENT_BUS.post(event);
        p_109092_.mulPose(event.getQuaternion());
    }
}