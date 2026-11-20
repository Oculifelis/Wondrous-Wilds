package com.ineffa.wondrouswilds.client.screen.ingame;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class NestBoxScreen extends AbstractContainerScreen<NestBoxScreenHandler> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(WondrousWilds.MOD_ID, "textures/gui/container/nest_box.png");

    public NestBoxScreen(NestBoxScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    // 26.1: the container screen draws its tooltip itself.
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
