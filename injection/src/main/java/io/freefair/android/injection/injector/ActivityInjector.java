package io.freefair.android.injection.injector;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import io.freefair.android.injection.helper.RClassHelper;

public class ActivityInjector extends AndroidViewInjector<Activity> {

	public ActivityInjector(Activity activity){
		super(null, activity, RClassHelper.fromActivity(activity));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {
		if(type.isAssignableFrom(Resources.Theme.class)){
			return (T) getObject().getTheme();
		}
		return super.resolveValue(type, instance);
	}

	@Override
	protected View findViewById(@IdRes int id) {
		return getObject().findViewById(id);
	}

}
