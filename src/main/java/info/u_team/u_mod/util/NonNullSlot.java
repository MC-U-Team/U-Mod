package info.u_team.u_mod.util;

import java.util.*;

public class NonNullSlot<T> {
	
	private List<T> list;
	
	@SafeVarargs
	public NonNullSlot(T first, T... others) {
		list = new ArrayList<>();
		list.add(first);
		for (T values : others) {
			list.add(values);
		}
	}
	
	public List<T> getList() {
		return list;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NonNullSlot)) {
			return false;
		}
		return list.equals(((NonNullSlot<?>) obj).list);
	}
	
}
