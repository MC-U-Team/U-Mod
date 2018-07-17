package info.u_team.u_mod.gui;

import java.io.IOException;
import java.util.*;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.*;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.*;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.*;

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
	private HashMap<EnumModeTab, Integer> impl_map = Maps.newHashMap();
	private ResourceLocation normal_background;
	private ResourceLocation used_background;
	
	public UGuiContainer(ContainerBase inventorySlotsIn) {
		super(inventorySlotsIn);
		impl_map.put(EnumModeTab.NORMAL, 0);
		if (inventorySlotsIn.tile instanceof IClientEnergy) {
			impl_map.put(EnumModeTab.ENERGY, impl_map.size());
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(used_background);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		if (this.getTab() == EnumModeTab.ENERGY) {
			IClientEnergy iclient = (IClientEnergy) getContainer().tile;
			this.drawTexturedModalRect(i + 9, j + 8, 0, 166, Math.round(158 * ((float) iclient.getImpl() / (float) iclient.getStorage().getMaxEnergyStored())), 20);
		}
		this.drawInBackground(this.getTab(), mouseX, mouseY, i, j);
	}
	
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	@Override
	public void initGui() {
		super.initGui();
		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}
	
	private void drawOverlay(int mouseX, int mouseY) {
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		
		impl_map.forEach((ttab, num) -> {
			if (mouseX > i + 28 * num && mouseX < i + 28 * num + 28 && mouseY > j && mouseY < j + 28) {
				this.drawHoveringText(I18n.format("modetab." + ttab.name() + ".name"), mouseX, mouseY);
			}
		});
	}
	
	private void drawForgroundTab() {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		this.drawTexturedModalRect(i + (28 * impl_map.get(this.getTab())), j, 28 * impl_map.get(this.getTab()), 32, 28, 32);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.translate(16, 0, 0);
		
		impl_map.forEach((ttab, num) -> {
			Block item = ttab.item;
			if (item != null) {
				this.renderItemModelIntoGUI(new ItemStack(item), i + (28 * num) - 10, j + 10);
			} else {
				this.renderItemModelIntoGUI(new ItemStack(this.getContainer().world.getBlockState(this.getContainer().pos).getBlock()), i + (28 * num) - 10, j + 10);
			}
		});
		GlStateManager.translate(-16, 0, 0);
	}
	
	protected void renderItemModelIntoGUI(ItemStack stack, int x, int y) {
		IBakedModel bakedmodel = this.itemRender.getItemModelWithOverrides(stack, null, null);
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.setupGuiTransform(x, y, bakedmodel.isGui3d());
		bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
		this.itemRender.renderItem(stack, bakedmodel);
		GlStateManager.disableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}
	
	private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d) {
		GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
		GlStateManager.translate(8.0F, 8.0F, 0.0F);
		GlStateManager.scale(1.0F, -1.0F, 1.0F);
		GlStateManager.scale(18.0F, 18.0F, 18.0F);
		
		if (isGui3d) {
			GlStateManager.enableLighting();
		} else {
			GlStateManager.disableLighting();
		}
	}
	
	private void drawTabs(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 24;
		this.drawTexturedModalRect(i, j, 0, 2, 28 * impl_map.size(), 32);
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
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawTabs(mouseX, mouseY);
		
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
			GlStateManager.translate(i, j, 0.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			this.hoveredSlot = null;
			int k = 240;
			int l = 240;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			
			for (int i1 = 0; i1 < this.getContainer().slots.get(getTab()).size(); ++i1) {
				Slot slot = this.getContainer().slots.get(getTab()).get(i1);
				
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
					itemstack.setCount(MathHelper.ceil(itemstack.getCount() / 2.0F));
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
				float f = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
				
				if (f >= 1.0F) {
					f = 1.0F;
					this.returningStack = ItemStack.EMPTY;
				}
				
				int l2 = this.returningStackDestSlot.xPos - this.touchUpX;
				int i3 = this.returningStackDestSlot.yPos - this.touchUpY;
				int l1 = this.touchUpX + (int) (l2 * f);
				int i2 = this.touchUpY + (int) (i3 * f);
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
	
	@Override
	protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_) {
		if (this.mc.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
			this.renderToolTip(this.hoveredSlot.getStack(), p_191948_1_, p_191948_2_);
		}
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
	
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
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
	
	/**
	 * Returns the slot at the given coordinates or null if there is none.
	 */
	private Slot getSlotAtPosition(int x, int y) {
		for (int i = 0; i < this.getContainer().slots.get(getTab()).size(); ++i) {
			Slot slot = this.getContainer().slots.get(getTab()).get(i);
			
			if (this.isMouseOverSlot(slot, x, y) && slot.isEnabled()) {
				return slot;
			}
		}
		
		return null;
	}
	
	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		boolean flag = this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseButton - 100);
		Slot slot = this.getSlotAtPosition(mouseX, mouseY);
		long i = Minecraft.getSystemTime();
		this.doubleClick = this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton;
		this.ignoreMouseUp = false;
		
		if (mouseButton == 0 || mouseButton == 1 || flag) {
			int j = this.guiLeft;
			int k = this.guiTop;
			boolean flag1 = this.hasClickedOutside(mouseX, mouseY, j, k);
			if (slot != null)
				flag1 = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
			int l = -1;
			
			if (slot != null) {
				l = slot.slotNumber;
			}
			
			if (flag1) {
				l = -999;
			}
			
			if (this.mc.gameSettings.touchscreen && flag1 && this.mc.player.inventory.getItemStack().isEmpty()) {
				this.mc.displayGuiScreen((GuiScreen) null);
				return;
			}
			
			if (l != -1) {
				if (this.mc.gameSettings.touchscreen) {
					if (slot != null && slot.getHasStack()) {
						this.clickedSlot = slot;
						this.draggedStack = ItemStack.EMPTY;
						this.isRightMouseClick = mouseButton == 1;
					} else {
						this.clickedSlot = null;
					}
				} else if (!this.dragSplitting) {
					if (this.mc.player.inventory.getItemStack().isEmpty()) {
						if (this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseButton - 100)) {
							this.handleMouseClick(slot, l, mouseButton, ClickType.CLONE);
						} else {
							boolean flag2 = l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
							ClickType clicktype = ClickType.PICKUP;
							
							if (flag2) {
								this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
								clicktype = ClickType.QUICK_MOVE;
							} else if (l == -999) {
								clicktype = ClickType.THROW;
							}
							
							this.handleMouseClick(slot, l, mouseButton, clicktype);
						}
						
						this.ignoreMouseUp = true;
					} else {
						this.dragSplitting = true;
						this.dragSplittingButton = mouseButton;
						this.dragSplittingSlots.clear();
						
						if (mouseButton == 0) {
							this.dragSplittingLimit = 0;
						} else if (mouseButton == 1) {
							this.dragSplittingLimit = 1;
						} else if (this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseButton - 100)) {
							this.dragSplittingLimit = 2;
						}
					}
				}
			}
		}
		
		this.lastClickSlot = slot;
		this.lastClickTime = i;
		this.lastClickButton = mouseButton;
		
		int d = (this.width - this.xSize) / 2;
		int f = (this.height - this.ySize) / 2 - 28;
		
		if (mouseButton == 0) {
			impl_map.forEach((ttab, num) -> {
				if (mouseX > d + 28 * num && mouseX < d + 28 * num + 28 && mouseY > f && mouseY < f + 28) {
					this.setModeTab(ttab);
				}
			});
		}
	}
	
	@Override
	protected boolean hasClickedOutside(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
		return p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.xSize || p_193983_2_ >= p_193983_4_ + this.ySize;
	}
	
	/**
	 * Called when a mouse button is pressed and the mouse is moved around.
	 * Parameters are : mouseX, mouseY, lastButtonClicked & timeSinceMouseClick.
	 */
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		Slot slot = this.getSlotAtPosition(mouseX, mouseY);
		ItemStack itemstack = this.mc.player.inventory.getItemStack();
		
		if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
			if (clickedMouseButton == 0 || clickedMouseButton == 1) {
				if (this.draggedStack.isEmpty()) {
					if (slot != this.clickedSlot && !this.clickedSlot.getStack().isEmpty()) {
						this.draggedStack = this.clickedSlot.getStack().copy();
					}
				} else if (this.draggedStack.getCount() > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
					long i = Minecraft.getSystemTime();
					
					if (this.currentDragTargetSlot == slot) {
						if (i - this.dragItemDropDelay > 500L) {
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
							this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
							this.dragItemDropDelay = i + 750L;
							this.draggedStack.shrink(1);
						}
					} else {
						this.currentDragTargetSlot = slot;
						this.dragItemDropDelay = i;
					}
				}
			}
		} else if (this.dragSplitting && slot != null && !itemstack.isEmpty() && (itemstack.getCount() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
			this.dragSplittingSlots.add(slot);
			this.updateDragSplitting();
		}
	}
	
	/**
	 * Called when a mouse button is released.
	 */
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state); // Forge, Call parent to release buttons
		Slot slot = this.getSlotAtPosition(mouseX, mouseY);
		int i = this.guiLeft;
		int j = this.guiTop;
		boolean flag = this.hasClickedOutside(mouseX, mouseY, i, j);
		if (slot != null)
			flag = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
		int k = -1;
		
		if (slot != null) {
			k = slot.slotNumber;
		}
		
		if (flag) {
			k = -999;
		}
		
		if (this.doubleClick && slot != null && state == 0 && this.inventorySlots.canMergeSlot(ItemStack.EMPTY, slot)) {
			if (isShiftKeyDown()) {
				if (!this.shiftClickedSlot.isEmpty()) {
					for (Slot slot2 : this.getContainer().slots.get(getTab())) {
						if (slot2 != null && slot2.canTakeStack(this.mc.player) && slot2.getHasStack() && slot2.isSameInventory(slot) && Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) {
							this.handleMouseClick(slot2, slot2.slotNumber, state, ClickType.QUICK_MOVE);
						}
					}
				}
			} else {
				this.handleMouseClick(slot, k, state, ClickType.PICKUP_ALL);
			}
			
			this.doubleClick = false;
			this.lastClickTime = 0L;
		} else {
			if (this.dragSplitting && this.dragSplittingButton != state) {
				this.dragSplitting = false;
				this.dragSplittingSlots.clear();
				this.ignoreMouseUp = true;
				return;
			}
			
			if (this.ignoreMouseUp) {
				this.ignoreMouseUp = false;
				return;
			}
			
			if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
				if (state == 0 || state == 1) {
					if (this.draggedStack.isEmpty() && slot != this.clickedSlot) {
						this.draggedStack = this.clickedSlot.getStack();
					}
					
					boolean flag2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
					
					if (k != -1 && !this.draggedStack.isEmpty() && flag2) {
						this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
						this.handleMouseClick(slot, k, 0, ClickType.PICKUP);
						
						if (this.mc.player.inventory.getItemStack().isEmpty()) {
							this.returningStack = ItemStack.EMPTY;
						} else {
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
							this.touchUpX = mouseX - i;
							this.touchUpY = mouseY - j;
							this.returningStackDestSlot = this.clickedSlot;
							this.returningStack = this.draggedStack;
							this.returningStackTime = Minecraft.getSystemTime();
						}
					} else if (!this.draggedStack.isEmpty()) {
						this.touchUpX = mouseX - i;
						this.touchUpY = mouseY - j;
						this.returningStackDestSlot = this.clickedSlot;
						this.returningStack = this.draggedStack;
						this.returningStackTime = Minecraft.getSystemTime();
					}
					
					this.draggedStack = ItemStack.EMPTY;
					this.clickedSlot = null;
				}
			} else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
				this.handleMouseClick((Slot) null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
				
				for (Slot slot1 : this.dragSplittingSlots) {
					this.handleMouseClick(slot1, slot1.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
				}
				
				this.handleMouseClick((Slot) null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
			} else if (!this.mc.player.inventory.getItemStack().isEmpty()) {
				if (this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(state - 100)) {
					this.handleMouseClick(slot, k, state, ClickType.CLONE);
				} else {
					boolean flag1 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
					
					if (flag1) {
						this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
					}
					
					this.handleMouseClick(slot, k, state, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
				}
			}
		}
		
		if (this.mc.player.inventory.getItemStack().isEmpty()) {
			this.lastClickTime = 0L;
		}
		
		this.dragSplitting = false;
	}
	
	/**
	 * Returns whether the mouse is over the given slot.
	 */
	private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
		return this.isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
	}
	
	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX,
	 * rectY, rectWidth, rectHeight, pointX, pointY
	 */
	@Override
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
		int i = this.guiLeft;
		int j = this.guiTop;
		pointX = pointX - i;
		pointY = pointY - j;
		return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
	}
	
	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		if (slotIn != null) {
			slotId = slotIn.slotNumber;
		}
		
		this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, mouseButton, type, this.mc.player);
	}
	
	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.player.closeScreen();
		}
		
		this.checkHotbarKeys(keyCode);
		
		if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
			if (this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(keyCode)) {
				this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, ClickType.CLONE);
			} else if (this.mc.gameSettings.keyBindDrop.isActiveAndMatches(keyCode)) {
				this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, ClickType.THROW);
			}
		}
	}
	
	/**
	 * Checks whether a hotbar key (to swap the hovered item with an item in the
	 * hotbar) has been pressed. If so, it swaps the given items. Returns true if a
	 * hotbar key was pressed.
	 */
	@Override
	protected boolean checkHotbarKeys(int keyCode) {
		if (this.mc.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null) {
			for (int i = 0; i < 9; ++i) {
				if (this.mc.gameSettings.keyBindsHotbar[i].isActiveAndMatches(keyCode)) {
					this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed() {
		if (this.mc.player != null) {
			this.inventorySlots.onContainerClosed(this.mc.player);
		}
	}
	
	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		
		if (!this.mc.player.isEntityAlive() || this.mc.player.isDead) {
			this.mc.player.closeScreen();
		}
	}
	
	@Override
	public ContainerBase getContainer() {
		return (ContainerBase) this.inventorySlots;
	}
	
}
