package org.figuramc.FiguraController.lua;

import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.NbtToLua;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.lua.docs.LuaTypeDoc;

import org.hid4java.HidDevice;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import net.minecraft.nbt.ByteArrayTag;

import org.figuramc.FiguraController.ControllerHolder;

@LuaWhitelist
@LuaTypeDoc(name = "ControllerAPI", value = "controller")
public class ControllerAPI {

    public static ControllerHolder controller = ControllerHolder.getInstance();

    public ControllerAPI() {
    }

    @LuaWhitelist
    @LuaMethodDoc("controller.read")
    public LuaValue read() {
        HidDevice hidDevice = controller.hidDevice;

        if (hidDevice == null)
            return null;

        if (hidDevice.isClosed())
            hidDevice.open();

        try {
            byte[] bytes = hidDevice.readAll(0);
            LuaValue table = NbtToLua.convert(new ByteArrayTag(bytes));
            return table;
        } catch (Exception e) {
            return null;
        }
    }

    @LuaWhitelist
    @LuaMethodDoc(overloads = @LuaMethodOverload(argumentTypes = { LuaValue.class, Integer.class }, argumentNames = {
            "data", "id" }), value = "controller.write")
    public int write(@LuaNotNil LuaValue data, int id) {
        HidDevice hidDevice = controller.hidDevice;

        if (!data.istable())
            throw new LuaError("Expected table, got " + data.typename());

        if (hidDevice == null) {
            return -1;
        }

        if (hidDevice.isClosed())
            hidDevice.open();

        LuaTable table = data.checktable();

        byte[] report = new byte[table.length()];
        int i = 0;
        for (LuaValue key : table.keys()) {
            report[i] = table.get(key).tobyte();
            i++;
        }

        return hidDevice.write(report, report.length, (byte) id);
    }

    @LuaWhitelist
    @LuaMethodDoc("controller.isConnected")
    public boolean isConnected() {
        return controller.connected;
    }

    @Override
    public String toString() {
        return "ControllerAPI";
    }
}