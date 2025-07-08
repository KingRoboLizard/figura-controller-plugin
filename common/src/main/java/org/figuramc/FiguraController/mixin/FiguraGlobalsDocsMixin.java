package org.figuramc.FiguraController.mixin;

import org.figuramc.FiguraController.lua.*;
import org.figuramc.figura.lua.docs.FiguraGlobalsDocs;
import org.figuramc.figura.lua.docs.LuaFieldDoc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FiguraGlobalsDocs.class, remap = false)
public class FiguraGlobalsDocsMixin {
    @LuaFieldDoc("globals.controller")
    public ControllerAPI controller;
}