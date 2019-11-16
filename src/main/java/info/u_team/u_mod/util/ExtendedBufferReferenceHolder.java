package info.u_team.u_mod.util;

import java.util.function.*;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;

public class ExtendedBufferReferenceHolder {
	
	public static final BufferReferenceHolder createFloatHolder(Supplier<Float> get, Consumer<Float> set) {
		return new BufferReferenceHolder() {
			
			@Override
			public PacketBuffer get() {
				return new PacketBuffer(Unpooled.copyFloat(get.get()));
			}
			
			@Override
			public void set(PacketBuffer buffer) {
				set.accept(buffer.readFloat());
			}
		};
	}
	
}
