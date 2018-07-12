package info.u_team.u_mod.resource;

import java.util.function.Consumer;

import info.u_team.u_mod.api.IResource;

public class ResourceUtil {
	
	public static void iterate(Consumer<IResource> consumer) {
		for (EnumResources1 resource : EnumResources1.VALUES) {
			consumer.accept(resource);
		}
		for (EnumResources2 resource : EnumResources2.VALUES) {
			consumer.accept(resource);
		}
	}
	
}
