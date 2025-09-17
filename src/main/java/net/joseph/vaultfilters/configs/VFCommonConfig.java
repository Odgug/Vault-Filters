package net.joseph.vaultfilters.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class VFCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> RESEARCH_NAME;
    static {
        BUILDER.push("Vault Filters Common Config");
        RESEARCH_NAME = BUILDER.comment("\nThe research filters will be locked behind +" +
                "\nLeave blank for no lock (the default)").define("Research Lock","");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
