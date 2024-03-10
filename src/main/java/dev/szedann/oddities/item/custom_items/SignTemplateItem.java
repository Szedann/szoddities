package dev.szedann.oddities.item.custom_items;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.message.FilterMask;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SignTemplateItem extends Item {
    public SignTemplateItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if(player == null) return ActionResult.PASS;
        ItemStack stack = player.getStackInHand(context.getHand());
        if(!(context.getWorld().getBlockEntity(context.getBlockPos()) instanceof SignBlockEntity signBlockEntity))
            return super.useOnBlock(context);
        if (player.isSneaking()){
            SignText text = signBlockEntity.getText(signBlockEntity.isPlayerFacingFront(player));
            NbtList list = new NbtList();
            Arrays.stream(text.getMessages(false)).map(Text::getString).map(NbtString::of).forEach(list::add);
            stack.setSubNbt("messages", list);
        }else if( stack.hasNbt() && stack.getNbt().getList("messages", NbtElement.STRING_TYPE) != null){
            if(signBlockEntity.isWaxed()) return ActionResult.FAIL;
            NbtCompound nbt = stack.getOrCreateNbt();
            NbtList list = nbt.getList("messages", NbtElement.STRING_TYPE);
            if(list == null) return ActionResult.FAIL;
            List<FilteredMessage> messages = list.stream().map(NbtElement::asString).map(e->new FilteredMessage(e, FilterMask.PASS_THROUGH)).toList();
            signBlockEntity.setEditor(player.getUuid());
            signBlockEntity.tryChangeText(player,
                    signBlockEntity.isPlayerFacingFront(player),
                    messages
            );
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.hasNbt() && stack.getNbt().getList("messages", NbtElement.STRING_TYPE) != null){
            NbtCompound nbt = stack.getOrCreateNbt();
            NbtList list = nbt.getList("messages", NbtElement.STRING_TYPE);
            if(list == null) return;
            list.stream().map(NbtElement::asString).map(Text::literal).map(text->text.formatted(Formatting.GRAY)).forEach(tooltip::add);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
