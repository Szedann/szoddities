package dev.szedann.oddities.mixin.client;

import dev.szedann.oddities.Szoddities;
import dev.szedann.oddities.item.SOItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useTemplateSignModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if ((stack.isOf(SOItems.SIGN_TEMPLATE) && (stack.getNbt() != null && !stack.getNbt().getList("messages", NbtElement.STRING_TYPE).stream().map(NbtElement::asString).allMatch(String::isEmpty)))) {
            return ((ItemRendererAccessor) this).szoddities$getModels().getModelManager().getModel(new ModelIdentifier(Szoddities.MOD_ID, "sign_template_filled", "inventory"));
        }
        return value;
    }
}