package io.freefair.android.injection.injector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import io.freefair.android.injection.helper.RClassHelper;

/**
 * @author Dennis Fricke
 */
public class WatchViewStubActivityInjector extends AndroidViewInjector<Activity> {

	private final WatchViewStub stub;

	public WatchViewStubActivityInjector(Activity activity, WatchViewStub stub, Injector parentInjector) {
		super(parentInjector, activity, RClassHelper.fromActivity(activity));
		this.stub = stub;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {
		if(type.isAssignableFrom(Context.class))
			return (T) stub.getContext();

		return super.resolveValue(type, instance);
	}

	@Override
	protected View findViewById(@IdRes int id) {
		return stub.findViewById(id);
	}

}
