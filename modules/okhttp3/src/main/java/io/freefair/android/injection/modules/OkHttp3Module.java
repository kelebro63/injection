package io.freefair.android.injection.modules;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;

import io.freefair.android.injection.InjectionModule;
import io.freefair.android.injection.injector.InjectionContainer;
import io.freefair.android.util.function.Consumer;
import io.freefair.android.util.function.Supplier;
import io.freefair.android.util.function.Suppliers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public class OkHttp3Module implements InjectionModule {

    private Consumer<OkHttpClient.Builder> configurator;

    /**
     * Create a new {@link OkHttp3Module} with the given configurator
     *
     * @param configurator A {@link Consumer} which will get the {@link OkHttpClient.Builder}
     *                     in order to perform further configuration
     */
    public OkHttp3Module(Consumer<OkHttpClient.Builder> configurator) {
        this.configurator = configurator;
    }

    @Override
    public void configure(InjectionContainer injectionContainer) {
        injectionContainer.registerSupplier(OkHttpClient.class, Suppliers.cache(new Supplier<OkHttpClient>() {
            @Nullable
            @Override
            public OkHttpClient get() {
                OkHttpClient.Builder client = new OkHttpClient.Builder();
                configurator.accept(client);
                return client.build();
            }
        }));
    }

    /**
     * @return An {@link OkHttp3Module} with an empty (default) configuration
     */
    public static OkHttp3Module withEmptyConfig() {
        return new OkHttp3Module(new Consumer<OkHttpClient.Builder>() {
            @Override
            public void accept(OkHttpClient.Builder okHttpClientBuilder) {
            }
        });
    }

    /**
     * Create an {@link OkHttp3Module} with an 10MB cache
     *
     * @param context Context of the current Application.
     *                Used for {@link Context#getCacheDir()}
     * @return An {@link OkHttp3Module} with an 10MB cache
     */
    public static OkHttp3Module withCache(final Context context) {
        return new OkHttp3Module(new Consumer<OkHttpClient.Builder>() {
            @Override
            public void accept(OkHttpClient.Builder okHttpClientBuilder) {
                File cacheDir = new File(context.getCacheDir(), "okhttp3");
                long size = 1024 * 1024 * 10;
                okHttpClientBuilder.cache(new Cache(cacheDir, size));
            }
        });
    }
}
