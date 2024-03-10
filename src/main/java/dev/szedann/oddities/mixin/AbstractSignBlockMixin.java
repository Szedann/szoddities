package dev.szedann.oddities.mixin;

import dev.szedann.oddities.item.SOItems;
import dev.szedann.oddities.item.custom_items.SignTemplateItem;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSignBlock.class)
public class AbstractSignBlockMixin {
    @Inject( method = "onUse", at = @At("HEAD"), cancellable = true)
    private void ignoreIfSignTemplateInHand(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if(player.getStackInHand(hand).isOf(SOItems.SIGN_TEMPLATE))
            cir.setReturnValue(ActionResult.PASS);
    }
}
