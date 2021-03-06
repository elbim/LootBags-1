package mal.lootbags.jei;

import javax.annotation.Nonnull;

import mal.lootbags.LootBags;
import mal.lootbags.LootbagsUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LootCategory extends BlankRecipeCategory<LootWrapper>{

	protected static final int FIRSTY = 46;
	protected static final int FIRSTX = 1;
	protected static final int WIDTH = 164;
	protected static final int HEIGHT = 127;
	protected static final int ITEMSPERROW = 10;
	protected static final int ITEMSPERCOL = 5;
	protected static final int SPACINGX=16;
	protected static final int SPACINGY=16;
	protected static final int ITEMSPERPAGE = ITEMSPERROW*ITEMSPERCOL;
	
	@Nonnull
	private final IDrawable background;
	
	public LootCategory(IGuiHelper guihelper)
	{	
		ResourceLocation location = new ResourceLocation(LootBags.MODID, "textures/gui/JEILootbagGui.png");
		background = guihelper.createDrawable(location, 0, 0, WIDTH, HEIGHT);
	}
	
	@Override
	public String getUid() {
		return JEILoot.LOOT;
	}

	@Override
	public String getTitle() {
		return LootbagsUtil.translateToLocal("jei.loot.title");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull LootWrapper wrapper, @Nonnull IIngredients ingredients) {
		int x = FIRSTX;
		int y = FIRSTY;
		
		layout.getItemStacks().init(0, true, 74, 2);
		for(int i = 1; i < Math.min(ITEMSPERPAGE, ingredients.getOutputs(ItemStack.class).size())+1; i++)
		{
			layout.getItemStacks().init(i, false, x, y);
			x+= SPACINGX;
			
			if(x >= FIRSTX+SPACINGX*ITEMSPERROW)
			{
				x = FIRSTX;
				y+= SPACINGY;
			}
		}
		
		layout.getItemStacks().set(0, wrapper.getInputs());
		int slots = Math.min(wrapper.amountOfItems(), ITEMSPERPAGE);
		for(int i = 0; i < slots; i++)
			layout.getItemStacks().set(i+1, wrapper.getItems(i, slots));
	}
}
