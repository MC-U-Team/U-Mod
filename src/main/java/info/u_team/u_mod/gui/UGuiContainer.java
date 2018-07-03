package info.u_team.u_mod.gui;

import java.io.IOException;
import java.util.Set;

import com.google.common.collect.Sets;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IUGui;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UGuiContainer extends GuiContainer implements IUGui {
	
	private static final ResourceLocation ENERGY = new ResourceLocation(UConstants.MODID, "textures/gui/energy.png");
	private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	
	/** holds the slot currently hovered */
	private Slot hoveredSlot;
	/** Used when touchscreen is enabled. */
	private Slot clickedSlot;
	/** Used when touchscreen is enabled. */
	private boolean isRightMouseClick;
	/** Used when touchscreen is enabled */
	private ItemStack draggedStack = ItemStack.EMPTY;
	private int touchUpX;
	private int touchUpY;
	private Slot returningStackDestSlot;
	private long returningStackTime;
	/** Used when touchscreen is enabled */
	private ItemStack returningStack = ItemStack.EMPTY;
	private Slot currentDragTargetSlot;
	private long dragItemDropDelay;
	protected final Set<Slot> dragSplittingSlots = Sets.<Slot> newHashSet();
	protected boolean dragSplitting;
	private int dragSplittingLimit;
	private int dragSplittingButton;
	private boolean ignoreMouseUp;
	private int dragSplittingRemnant;
	private long lastClickTime;
	private Slot lastClickSlot;
	private int lastClickButton;
	private boolean doubleClick;
	private ItemStack shiftClickedSlot = ItemStack.EMPTY;
	
	private ResourceLocation normal_background;
	private ResourceLocation used_background;
	
	public UGuiContainer(ContainerBase inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(used_background);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		this.drawInBackground(this.getTab(), mouseX, mouseY, i, j);
	}
	
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		drawTabs(mouseX, mouseY);
		
		if (this.getTab() == EnumModeTab.NORMAL) {
			int i = this.guiLeft;
			int j = this.guiTop;
			this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			super.drawScreen(mouseX, mouseY, partialTicks);
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) i, (float) j, 0.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			this.hoveredSlot = null;
			int k = 240;
			int l = 240;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			
			for (EnumModeTab key : EnumModeTab.values()) {
				for (int i1 = 0; i1 < this.getContainer().slots.get(key).size(); ++i1) {
					Slot slot = this.getContainer().slots.get(key).get(i1);
					
					if (slot.isEnabled()) {
						this.drawSlot(slot);
					}
					
					if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.isEnabled()) {
						this.hoveredSlot = slot;
						GlStateManager.disableLighting();
						GlStateManager.disableDepth();
						int j1 = slot.xPos;
						int k1 = slot.yPos;
						GlStateManager.colorMask(true, true, true, false);
						this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
						GlStateManager.colorMask(true, true, true, true);
						GlStateManager.enableLighting();
						GlStateManager.enableDepth();
					}
				}
			}
			
			RenderHelper.disableStandardItemLighting();
			this.drawGuiContainerForegroundLayer(mouseX, mouseY);
			RenderHelper.enableGUIStandardItemLighting();
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, mouseX, mouseY));
			InventoryPlayer inventoryplayer = this.mc.player.inventory;
			ItemStack itemstack = this.draggedStack.isEmpty() ? inventoryplayer.getItemStack() : this.draggedStack;
			
			if (!itemstack.isEmpty()) {
				int j2 = 8;
				int k2 = this.draggedStack.isEmpty() ? 8 : 16;
				String s = null;
				
				if (!this.draggedStack.isEmpty() && this.isRightMouseClick) {
					itemstack = itemstack.copy();
					itemstack.setCount(MathHelper.ceil((float) itemstack.getCount() / 2.0F));
				} else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
					itemstack = itemstack.copy();
					itemstack.setCount(this.dragSplittingRemnant);
					
					if (itemstack.isEmpty()) {
						s = "" + TextFormatting.YELLOW + "0";
					}
				}
				
				this.drawItemStack(itemstack, mouseX - i - 8, mouseY - j - k2, s);
			}
			
			if (!this.returningStack.isEmpty()) {
				float f = (float) (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
				
				if (f >= 1.0F) {
					f = 1.0F;
					this.returningStack = ItemStack.EMPTY;
				}
				
				int l2 = this.returningStackDestSlot.xPos - this.touchUpX;
				int i3 = this.returningStackDestSlot.yPos - this.touchUpY;
				int l1 = this.touchUpX + (int) ((float) l2 * f);
				int i2 = this.touchUpY + (int) ((float) i3 * f);
				this.drawItemStack(this.returningStack, l1, i2, (String) null);
			}
			
			GlStateManager.popMatrix();
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			
		} else if (this.getTab() == EnumModeTab.ENERGY) {
			this.drawEnergyTab(partialTicks, mouseX, mouseY);
		}
		
		drawForgroundTab();
		drawOverlay(mouseX, mouseY);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	/**
	 * Returns whether the mouse is over the given slot.
	 */
	private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
		return this.isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
	}
	
	/**
	 * Draws the given slot: any item in it, the slot's background, the hovered
	 * highlight, etc.
	 */
	private void drawSlot(Slot slotIn) {
		int i = slotIn.xPos;
		int j = slotIn.yPos;
		ItemStack itemstack = slotIn.getStack();
		boolean flag = false;
		boolean flag1 = slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick;
		ItemStack itemstack1 = this.mc.player.inventory.getItemStack();
		String s = null;
		
		if (slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty()) {
			itemstack = itemstack.copy();
			itemstack.setCount(itemstack.getCount() / 2);
		} else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && !itemstack1.isEmpty()) {
			if (this.dragSplittingSlots.size() == 1) {
				return;
			}
			
			if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
				itemstack = itemstack1.copy();
				flag = true;
				Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack().isEmpty() ? 0 : slotIn.getStack().getCount());
				int k = Math.min(itemstack.getMaxStackSize(), slotIn.getItemStackLimit(itemstack));
				
				if (itemstack.getCount() > k) {
					s = TextFormatting.YELLOW.toString() + k;
					itemstack.setCount(k);
				}
			} else {
				this.dragSplittingSlots.remove(slotIn);
				this.updateDragSplitting();
			}
		}
		
		this.zLevel = 100.0F;
		this.itemRender.zLevel = 100.0F;
		
		if (itemstack.isEmpty() && slotIn.isEnabled()) {
			TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();
			
			if (textureatlassprite != null) {
				GlStateManager.disableLighting();
				this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
				this.drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
				GlStateManager.enableLighting();
				flag1 = true;
			}
		}
		
		if (!flag1) {
			if (flag) {
				drawRect(i, j, i + 16, j + 16, -2130706433);
			}
			
			GlStateManager.enableDepth();
			this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, itemstack, i, j);
			this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, i, j, s);
		}
		
		this.itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}
	
	/**
	 * Draws an ItemStack.
	 * 
	 * The z index is increased by 32 (and not decreased afterwards), and the item
	 * is then rendered at z=200.
	 */
	private void drawItemStack(ItemStack stack, int x, int y, String altText) {
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		this.itemRender.zLevel = 200.0F;
		net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null)
			font = fontRenderer;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack.isEmpty() ? 0 : 8), altText);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}
	
	private void updateDragSplitting() {
		ItemStack itemstack = this.mc.player.inventory.getItemStack();
		
		if (!itemstack.isEmpty() && this.dragSplitting) {
			if (this.dragSplittingLimit == 2) {
				this.dragSplittingRemnant = itemstack.getMaxStackSize();
			} else {
				this.dragSplittingRemnant = itemstack.getCount();
				
				for (Slot slot : this.dragSplittingSlots) {
					ItemStack itemstack1 = itemstack.copy();
					ItemStack itemstack2 = slot.getStack();
					int i = itemstack2.isEmpty() ? 0 : itemstack2.getCount();
					Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
					int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
					
					if (itemstack1.getCount() > j) {
						itemstack1.setCount(j);
					}
					
					this.dragSplittingRemnant -= itemstack1.getCount() - i;
				}
			}
		}
	}
	
	private void drawOverlay(int mouseX, int mouseY) {
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		
		for (EnumModeTab ttab : EnumModeTab.values()) {
			if (mouseX > i + 28 * ttab.ordinal() && mouseX < i + 28 * ttab.ordinal() + 28 && mouseY > j && mouseY < j + 28) {
				this.drawHoveringText(I18n.format("modetab." + ttab.name() + ".name"), mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		
		if (mouseButton == 0) {
			for (EnumModeTab ttab : EnumModeTab.values()) {
				if (mouseX > i + 28 * ttab.ordinal() && mouseX < i + 28 * ttab.ordinal() + 28 && mouseY > j && mouseY < j + 28) {
					this.setModeTab(ttab);
				}
			}
		}
	}
	
	private void drawForgroundTab() {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		this.drawTexturedModalRect(i + (28 * this.getTab().ordinal()), j, 28 * this.getTab().ordinal(), 32, 28, 32);
	}
	
	private void drawTabs(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 24;
		this.drawTexturedModalRect(i, j, 0, 2, 28 * EnumModeTab.values().length, 32);
	}
	
	public final void setBackground(ResourceLocation background) {
		this.normal_background = background;
		if (this.getTab() == EnumModeTab.NORMAL) {
			this.used_background = this.normal_background;
		}
	}
	
	public final EnumModeTab getTab() {
		return this.getContainer().getTab();
	}
	
	public final void setModeTab(EnumModeTab tab) {
		this.getContainer().setTab(tab);
		initTab(tab);
	}
	
	private void initTab(EnumModeTab tab) {
		if (tab == EnumModeTab.NORMAL) {
			this.used_background = normal_background;
		} else if (tab == EnumModeTab.ENERGY) {
			this.used_background = ENERGY;
		}
	}
	
	private void drawEnergyTab(float partialTicks, int mouseX, int mouseY) {
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
	}
	
	@Override
	public ContainerBase getContainer() {
		return (ContainerBase) this.inventorySlots;
	}
	
}
