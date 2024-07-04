package net.joseph.vaultfilters.configs;

import com.simibubi.create.content.contraptions.pulley.PulleyBlock;
import net.minecraftforge.common.ForgeConfigSpec;

public class VFServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_CACHES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> MR_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RS_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BACKPACKS_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> AE2_COMPAT;

    static {
        BUILDER.push("Vault Filters Server Config");

        MAX_CACHES = BUILDER.comment("\nMaximum amount of filters that can be cached on an item" +
                "\nNone: 0" +
                "\nDefault: 4").defineInRange("Max Filter Caches",4,0,20);

        MR_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside Modular Router modules" +
                "\nDefault:true").define("Modular Routers Compatibility",true);

        RS_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside Refined Storage exporters and grid filters" +
                "\nDefault:true").define("Refined Storage Compatibility", true);

        BACKPACKS_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside sophisticated backpacks upgrades" +
                "\nDefault:true").define("Backpacks Compatibility",true);

        AE2_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside AE2 exporter buses and terminal filters" +
                "\nDefault:true").define("Refined Storage Compatibility", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
