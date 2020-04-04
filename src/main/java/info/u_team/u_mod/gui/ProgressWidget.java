package info.u_team.u_mod.gui;

import java.util.function.Supplier;

import info.u_team.u_mod.UMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ProgressWidget extends Widget {
	
	private static final ResourceLocation LONG_BASIC_ARROW = new ResourceLocation(UMod.MODID, "textures/gui/progress/long_basic_arrow.png");
	
	public final ResourceLocation texture;
	
	private final Supplier<Float> progressSupplier;
	
	public ProgressWidget(int x, int y, int width, int height, ResourceLocation texture, Supplier<Float> progress) {
		super(x, y, width, height, "");
		this.texture = texture;
		this.progressSupplier = progress;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(texture);
		final float progress = Math.min(Math.max(progressSupplier.get(), 0), 1);
		blit(x, y, 0, 0, width, height, width, height * 2);
		blit(x, y, 0, height, (int) (width * progress), height, width, height * 2);
	}
	
	@Override
	public void playDownSound(SoundHandler handler) {
		// Don't play click sound
	}
	
	public static ProgressWidget createBasicArrow(int x, int y, int width, int height, Supplier<Float> progress) {
		return new ProgressWidget(x, y, width, height, LONG_BASIC_ARROW, progress);
	}
}
