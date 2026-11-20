package com.ineffa.wondrouswilds.client.screen.ingame;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class NestBoxScreen extends AbstractContainerScreen<NestBoxScreenHandler> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "textures/gui/container/nest_box.png");

    public NestBoxScreen(NestBoxScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
        // 1.20.2+: Screen#render draws the background itself; drawing it again here would blur twice.
        super.render(matrices, mouseX, mouseY, delta);

        this.renderTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics matrices, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        matrices.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
