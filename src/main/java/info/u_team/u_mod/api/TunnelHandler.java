package info.u_team.u_mod.api;

import java.util.*;

import com.google.common.collect.Lists;

import info.u_team.u_mod.block.BlockEnergyPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class TunnelHandler {
	
	private static HashMap<Integer, ArrayList<BlockPos>> TUNNELS = new HashMap<Integer, ArrayList<BlockPos>>();
	private static int highestid = -1;
	
	private static int mergeTunnels(World world, int id, int id2) {
		int min = Math.min(id, id2);
		int max = Math.max(id, id2);
		ArrayList<BlockPos> first = TUNNELS.get(min);
		ArrayList<BlockPos> second = TUNNELS.get(max);
		if (first == null || second == null)
			return -1;
		first.addAll(second);
		second.forEach(pos -> {
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				cable.setID(min);
			}
		});
		second.clear();
		TUNNELS.remove(max);
		return min;
	}
	
	public static void registerTunnel(int id, BlockPos pos, World world) {
		if (world != null && world.isRemote)
			return;
		ArrayList<BlockPos> posses;
		if ((posses = TUNNELS.get(id)) == null) {
			TUNNELS.put(id, Lists.newArrayList(pos));
		} else {
			posses.add(pos);
		}
	}
	
	public static boolean onStateChange(IBlockState state, World world, BlockPos pos) {
		ICable cable1 = (ICable) world.getTileEntity(pos);
		if (cable1 == null || world.isRemote)
			return false;
		int id = cable1.getID();
		state = state.getActualState(world, pos);
		if (state.getValue(BlockEnergyPipe.UP)) {
			TileEntity entity = world.getTileEntity(pos.down());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID() && cable.getID() > -1) {
						id = cable.getID();
					}
				}
			}
		}
		if (state.getValue(BlockEnergyPipe.DOWN)) {
			TileEntity entity = world.getTileEntity(pos.up());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID()) {
						id = mergeTunnels(world, cable.getID(), id);
					} else {
						id = cable.getID();
					}
				}
			}
		}
		if (state.getValue(BlockEnergyPipe.EAST)) {
			TileEntity entity = world.getTileEntity(pos.west());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID()) {
						id = mergeTunnels(world, cable.getID(), id);
					} else {
						id = cable.getID();
					}
				}
			}
		}
		if (state.getValue(BlockEnergyPipe.WEST)) {
			TileEntity entity = world.getTileEntity(pos.east());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID()) {
						id = mergeTunnels(world, cable.getID(), id);
					} else {
						id = cable.getID();
					}
				}
			}
		}
		if (state.getValue(BlockEnergyPipe.NORTH)) {
			TileEntity entity = world.getTileEntity(pos.south());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID()) {
						id = mergeTunnels(world, cable.getID(), id);
					} else {
						id = cable.getID();
					}
				}
			}
		}
		if (state.getValue(BlockEnergyPipe.SOUTH)) {
			TileEntity entity = world.getTileEntity(pos.north());
			if (entity != null && entity instanceof ICable) {
				ICable cable = (ICable) entity;
				if (cable.getID() > -1) {
					if (id > -1 && id != cable.getID()) {
						id = mergeTunnels(world, cable.getID(), id);
					} else {
						id = cable.getID();
					}
				}
			}
		}
		
		if (cable1.getID() != id || cable1.getID() == -1) {
			if (id == -1) {
				id = (highestid += 1);
				TUNNELS.put(id, Lists.newArrayList(pos));
			} else {
				ArrayList<BlockPos> blocks = TUNNELS.get(id);
				if (blocks != null) {
					blocks.add(pos);
				} else {
					return onStateChange(world.getBlockState(pos), world, pos);
				}
			}
			
			cable1.setID(id);
			return true;
		}
		return false;
	}
	
	public static void notifyOfDestruction(IBlockState state, World world, BlockPos pos) {
		if (world.isRemote)
			return;
		byte x = 0;
		state = state.getActualState(world, pos);
		ICable cable = (ICable) world.getTileEntity(pos);
		
		if (state.getValue(BlockEnergyPipe.UP)) {
			x++;
		}
		if (state.getValue(BlockEnergyPipe.DOWN)) {
			x++;
		}
		if (state.getValue(BlockEnergyPipe.EAST)) {
			x++;
		}
		if (state.getValue(BlockEnergyPipe.WEST)) {
			x++;
		}
		if (state.getValue(BlockEnergyPipe.NORTH)) {
			x++;
		}
		if (state.getValue(BlockEnergyPipe.SOUTH)) {
			x++;
		}
		
		int id1 = cable.getID();
		if (x > 1) {
			ArrayList<BlockPos> tunnel = TUNNELS.get(id1);
			if (tunnel == null) {
				notifyOfDestruction(world.getBlockState(pos), world, pos);
				return;
			}
			tunnel.forEach(pos2 -> {
				TileEntity entity = world.getTileEntity(pos2);
				if (entity != null && entity instanceof ICable) {
					ICable cable2 = (ICable) entity;
					cable2.setID(-1);
				}
			});
			tunnel.forEach(pos2 -> {
				IBlockState state2 = world.getBlockState(pos2);
				state2.getBlock().onNeighborChange(world, pos2, null);
			});
			tunnel.clear();
			TUNNELS.remove(new Integer(id1));
		} else if (x == 1) {
			cable.setID(-1);
			ArrayList<BlockPos> tunnel = TUNNELS.get(id1);
			if (tunnel != null) {
				tunnel.remove(pos);
			}
		} else {
			cable.setID(-1);
			TUNNELS.remove(new Integer(id1)).clear();
		}
	}
	
	private static int energy_available;
	private static int energy_needed;
	private static HashMap<Integer, ArrayList<BlockPos>> __impl_transform;
	private static ArrayList<BlockPos> posses;
	
	@SubscribeEvent
	public static void onWorldTIck(WorldTickEvent event) {
		if (event.world.isRemote)
			return;
		__impl_transform = (HashMap<Integer, ArrayList<BlockPos>>) TUNNELS.clone();
		__impl_transform.forEach((id, array) -> {
			energy_needed = 0;
			energy_available = 0;
			posses = (ArrayList<BlockPos>) array.clone();
			posses.forEach(pos -> {
				ICable entity = (ICable) event.world.getTileEntity(pos);
				if (entity == null)
					return;
				EnumFacing[] facings = entity.isInput();
				for (EnumFacing face : facings) {
					ICableExceptor exceptor = (ICableExceptor) event.world.getTileEntity(pos.offset(face));
					int rate = Math.min(exceptor.rate(), entity.rate());
					IEnergyStorage storage = exceptor.getStorage();
					energy_available += storage.extractEnergy(rate, true);
				}
			});
			posses.forEach(pos -> {
				ICable entity = (ICable) event.world.getTileEntity(pos);
				if (entity == null)
					return;
				EnumFacing[] facings = entity.isOutput();
				for (EnumFacing face : facings) {
					ICableExceptor exceptor = (ICableExceptor) event.world.getTileEntity(pos.offset(face));
					int rate = Math.min(energy_available, Math.min(exceptor.rate(), entity.rate()));
					IEnergyStorage storage = exceptor.getStorage();
					energy_needed += storage.receiveEnergy(rate, false);
				}
			});
			posses.forEach(pos -> {
				ICable entity = (ICable) event.world.getTileEntity(pos);
				if (entity == null)
					return;
				EnumFacing[] facings = entity.isInput();
				for (EnumFacing face : facings) {
					ICableExceptor exceptor = (ICableExceptor) event.world.getTileEntity(pos.offset(face));
					int rate = Math.min(energy_needed, Math.min(exceptor.rate(), entity.rate()));
					IEnergyStorage storage = exceptor.getStorage();
					energy_needed -= storage.extractEnergy(rate, false);
				}
			});
		});
	}
	
}
