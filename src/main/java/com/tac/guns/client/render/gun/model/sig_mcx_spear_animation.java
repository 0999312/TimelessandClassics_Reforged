package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.item.GunItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class sig_mcx_spear_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrices, MultiBufferSource renderBuffer, int light, int overlay)
    {
        
        
        if(Gun.getScope(stack) == null)
        {
            RenderUtil.renderModel(SpecialModels.MCX_SPEAR_FSIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
            RenderUtil.renderModel(SpecialModels.MCX_SPEAR_RSIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        RenderUtil.renderModel(SpecialModels.MCX_SPEAR.getModel(), stack, matrices, renderBuffer, light, overlay);
        //RenderUtil.renderModel(SpecialModels.M4_BOLT_HANDLE.getModel(), stack, matrices, renderBuffer, light, overlay);

        matrices.pushPose();
        Gun gun = ((GunItem) stack.getItem()).getGun();
        float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());

        if(Gun.hasAmmo(stack))
        {
            // Math provided by Bomb787 on GitHub and Curseforge!!!
            matrices.translate(0, 0, 0.225f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
        }
        else if(!Gun.hasAmmo(stack))
        {
            if(cooldownOg > 0.5){
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, 0.225f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
            }
            else
            {
                matrices.translate(0, 0, 0.225f * (-4.5 * Math.pow(0.5-0.5, 2) + 1.0));
            }
        }

        RenderUtil.renderModel(SpecialModels.MCX_SPEAR_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.popPose();
    }
}
