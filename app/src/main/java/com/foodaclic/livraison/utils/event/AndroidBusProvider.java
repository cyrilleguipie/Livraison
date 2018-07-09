package com.foodaclic.livraison.utils.event;

/**
 * Created by cyrilleguipie on 6/15/16.
 */

public final class AndroidBusProvider {

    private static final AndroidBus BUS = new AndroidBus();

    public static AndroidBus getInstance() {
        return BUS;
    }

    private AndroidBusProvider() {
        // No instances.
    }
}
