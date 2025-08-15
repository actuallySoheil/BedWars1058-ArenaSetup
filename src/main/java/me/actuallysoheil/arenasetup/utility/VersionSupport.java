package me.actuallysoheil.arenasetup.utility;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.bukkit.Bukkit;

@UtilityClass
public class VersionSupport {

    public boolean legacy() {
        val packageName = Bukkit.getServer().getClass().getPackage().getName();
        val version = packageName.substring(packageName.lastIndexOf('.') + 1);
        return version.equalsIgnoreCase("v1_8_R3")
                || version.equalsIgnoreCase("v1_9_R1")
                || version.equalsIgnoreCase("v1_10_R1")
                || version.equalsIgnoreCase("v1_11_R1")
                || version.equalsIgnoreCase("v1_12_R1");
    }

}