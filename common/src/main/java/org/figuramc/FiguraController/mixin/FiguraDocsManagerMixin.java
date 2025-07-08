package org.figuramc.FiguraController.mixin;

import org.figuramc.FiguraController.lua.*;
import org.figuramc.figura.lua.docs.FiguraDocsManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mixin(value = FiguraDocsManager.class, remap = false)
public class FiguraDocsManagerMixin {
    @Shadow
    @Final
    public static Map<String, Collection<Class<?>>> GLOBAL_CHILDREN;

    @Shadow
    @Final
    private static Map<Class<?>, String> NAME_MAP;

    static {
        GLOBAL_CHILDREN.put("controller", List.of(
                ControllerAPI.class));
    }
}