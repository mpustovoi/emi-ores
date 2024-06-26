package cc.abbie.emi_ores.compat.emi.stack;

import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class BiomeEmiStack extends EmiStack {
    private final Biome biome;

    private BiomeEmiStack(Biome biome) {
        this.biome = biome;
    }

    public static EmiStack of(Biome biome) {
        return new BiomeEmiStack(biome);
    }

    @Override
    public EmiStack copy() {
        return new BiomeEmiStack(biome);
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float delta, int flags) {
        int secondary;

        int water = biome.getWaterColor();
        int fog = biome.getFogColor();
        if (fog != 12638463) {
            secondary = fog;
        } else {
            secondary = water;
        }

        gui.fillGradient(x, y, x+16, y+16, biome.getFoliageColor() | 0xff000000, secondary | 0xff000000);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public CompoundTag getNbt() {
        return null;
    }

    @Override
    public Object getKey() {
        return biome;
    }

    @Override
    public ResourceLocation getId() {
        return Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getKey(biome);
    }

    @Override
    public List<Component> getTooltipText() {
        return null;
    }

    @Override
    public List<ClientTooltipComponent> getTooltip() {
        List<ClientTooltipComponent> list = new ArrayList<>();
        list.add(ClientTooltipComponent.create(getName().getVisualOrderText()));
        if (Minecraft.getInstance().options.advancedItemTooltips) {
            list.add(ClientTooltipComponent.create(Component.literal(getId().toString()).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText()));
        }
        EmiTooltipComponents.appendModName(list, getId().getNamespace());
        list.addAll(super.getTooltip());
        return list;
    }

    @Override
    public Component getName() {
        return Component.translatable(getId().toLanguageKey("biome"));
    }
}
