package edu.mtu.tinventory.data;

import static edu.mtu.tinventory.gui.View.*;

import edu.mtu.tinventory.gui.View;
import edu.mtu.tinventory.util.StringUtils;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class Access {
	//TODO: Maybe change/expand on these...or make them dynamically defined? Something...
	public enum Level {
		SALESMAN(VIEW_INV, SELL_INV, VIEW_CUSTOMERS, VIEW_INVOICES),
		MANAGER(SALESMAN, CREATE_PRODUCT, UPDATE_PRODUCT, REPORTSCONTROLLER),
		ADMINISTRATOR(MANAGER, ADMIN); // TODO: Probably some stuff later

		private Set<View> views;

		Level(View... views) {
			this(null, views);
		}

		Level(Level level, View... views) {
			this.views = EnumSet.noneOf(View.class); //Just creates an empty set, name is slightly misleading
			if(level != null) {
				this.views.addAll(level.views);
			}
			if(views.length > 0) {
				this.views.addAll(Arrays.asList(views));
			}
		}

		public boolean hasAccess(View view) {
			return views.contains(view);
		}
	}

	// The baseline level of access
	private Level level;
	// And overrides or additions to this specific access level.
	private Set<View> additional;

	public static Access createFromString(String level, String overrides) {
		Access ret = new Access(Level.valueOf(level));
		if (!StringUtils.isNullOrEmpty(overrides)) {
			for (String s : overrides.split(";")) {
				ret.grantAccess(View.valueOf(s));
			}
		}
		return ret;
	}

	public Access(Level level, View... views) {
		this.level = level;
		if(views.length > 1) {
			this.additional = EnumSet.of(views[0], Arrays.copyOfRange(views, 1, views.length));
		} else if(views.length == 1) {
			this.additional = EnumSet.of(views[0]);
		} else {
			this.additional = EnumSet.noneOf(View.class);
		}
	}

	public Level getLevel() {
		return level;
	}

	public boolean hasAccess(View view) {
		return level.hasAccess(view) || additional.contains(view);
	}

	/**
	 * Grants access to a specific view.
	 * @param view The view to grant access to
	 * @return true if access has been granted, false if access has already been granted.
	 */
	public boolean grantAccess(View view) {
		return !hasAccess(view) && additional.add(view);
	}

	/**
	 * Revokes access to a specific view. You cannot revoke access to a view that is attached to the base level. TODO: Maybe change that?
	 * @param view The view to revoke access to
	 * @return true if access has been revoked, false if access was already revoked, or if the view is part of the base level.
	 */
	public boolean revokeAccess(View view) {
		return !level.hasAccess(view) && additional.contains(view) && additional.remove(view);
	}

	public String getOverridesString() {
		if(!additional.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (View view : additional) {
				sb.append(";").append(view.name());
			}
			return sb.substring(1);
		} else {
			return "";
		}
	}
}
