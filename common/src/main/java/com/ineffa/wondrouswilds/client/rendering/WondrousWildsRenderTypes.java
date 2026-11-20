package com.ineffa.wondrouswilds.client.rendering;

import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class WondrousWildsRenderTypes {

    /** Full block and sky light, as the eyes shader drew it. */
    public static final int FULL_BRIGHT = 0xF000F0;

    private WondrousWildsRenderTypes() {}

    // Translucent, unlit, no depth write: the same look as the old translucent eyes-shader glow.
    public static RenderType getTranslucentGlow(Identifier texture) {
        return AutoGlowingGeoLayer.EmissiveRenderType.getRenderType(texture, false, false, false);
    }
}
