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

    static {
        BUILDER.push("Vault Filters Server Config");

        MAX_CACHES = BUILDER.comment("Maximum amount of filters that can be cached on an item" +
                "\nNone: 0" +
                "\nDefault: 4").defineInRange("Max Filter Caches",4,0,20);

        MR_COMPAT = BUILDER.comment("Enable compatibility for list and attribute filters inside Modular Router modules" +
                "\nDefault:true").define("Routers Compatibility",true);
        RS_COMPAT = BUILDER.comment("Enable compatibility for list and attribute filters inside RS exporters and grid filters" +
                "\nDefault:true").define("RS Compatibility", true);

        BACKPACKS_COMPAT = BUILDER.comment("Enable compatibility for list and attribute filters inside sophisticated backpacks upgrades" +
                "\nDefault:true").define("Backpacks Compatibility",true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
