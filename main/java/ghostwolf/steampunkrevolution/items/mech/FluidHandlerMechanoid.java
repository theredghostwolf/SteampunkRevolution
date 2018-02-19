package ghostwolf.steampunkrevolution.items.mech;

import ghostwolf.steampunkrevolution.init.ModFluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class FluidHandlerMechanoid extends FluidHandlerItemStack {

	public FluidHandlerMechanoid(ItemStack container, int capacity) {
		super(container, capacity);
		
		
		   // if container was constructed by loading from NBT, should already
	     // have fluid information in tags
	     if (getFluidStack() == null)
	     {
	      setContainerToEmpty(); // start empty
	     }
	    }
	 
	    @Override
	    protected void setContainerToEmpty()
	    {
	        setFluidStack(new FluidStack(FluidRegistry.WATER,0)); // some code looks at level, some looks at lack of handler (tag)
	        container.getTagCompound().removeTag(FLUID_NBT_KEY);
	    }

	    @Override
	    public boolean canFillFluidType(FluidStack fluid)
	    {
	        return (fluid.getFluid() == FluidRegistry.getFluid("steam"));
	    }
	    
	    // rename getFluid() method since it is confusing as it returns a fluid stack
	    public FluidStack getFluidStack()
	    {
	     return getFluid();
	    }
	    
	    // rename setFluid() method since it is confusing as it take a fluid stack
	    public void setFluidStack(FluidStack parFluidStack)
	    {
	     setFluid(parFluidStack);
	    }
	

}
