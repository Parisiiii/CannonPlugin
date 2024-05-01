package org.cannon.utils;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NBTGetter {
    private static Method nmsCopyMethod;
    static {
        NMSReflect nmsReflect = new NMSReflect();
        Class craftItemStackClass = nmsReflect.getCraftBukkitClass("inventory", "CraftItemStack");
        try {
            nmsCopyMethod = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public static String getTagValue(String tag, ItemStack itemStack) {
        NMSReflect nmsReflect = new NMSReflect();
        try {
            Object nmsCopy = nmsCopyMethod.invoke(null,itemStack);
            if (nmsCopy != null) {
                Object tagCompound = nmsCopy.getClass().getMethod("getTag").invoke(nmsCopy);
                if (tagCompound != null) {
                    return (String) tagCompound.getClass().getMethod("getString", String.class).invoke(tagCompound, tag);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
