package org.figuramc.exampleplugin.fabric;

import org.figuramc.FiguraController.ControllerPlugin;

import net.fabricmc.api.ModInitializer;

/**
 * A mod class is not technically needed for Fabric to load the Plugin, but it's
 * still nice to have.
 */
public class ExamplePluginFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ControllerPlugin.init();
    }
}
