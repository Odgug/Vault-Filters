package net.joseph.vaultfilters.configs;

import com.simibubi.create.content.contraptions.pulley.PulleyBlock;
import net.minecraftforge.common.ForgeConfigSpec;

public class VFServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> CACHE_TTK;
    public static final ForgeConfigSpec.ConfigValue<Boolean> MR_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RS_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BACKPACKS_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> AE2_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> TOMS_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LASERIO_COMPAT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CACHE_DATAFIX;

    static {
        BUILDER.push("Vault Filters Server Config");

        CACHE_TTK = BUILDER.comment("\nHow long till an unused cache entry gets cleared" +
                "\nin minutes" +
                "\nMinimum 2" +
                "\nDefault: 5").defineInRange("Cache time to kill",5,2,100);

        MR_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside Modular Router modules" +
                "\nDefault:true").define("Modular Routers Compatibility",true);

        RS_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside Refined Storage exporters and grid filters" +
                "\nDefault:true").define("Refined Storage Compatibility", true);

        BACKPACKS_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside sophisticated backpacks upgrades" +
                "\nDefault:true").define("Backpacks Compatibility",true);

        AE2_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside AE2 exporter buses and terminal filters" +
                "\nDefault:true").define("AE2 Compatibility", true);

        TOMS_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside Tom's filtered inventory connector" +
                "\nDefault:true").define("Tom's Simple Storage Compatibility", true);

        LASERIO_COMPAT = BUILDER.comment("\nEnable compatibility for list and attribute filters inside LaserIO's card filters" +
                "\nDefault:true").define("LaserIO Compatibility", true);

        CACHE_DATAFIX = BUILDER.comment("\nDelete old cache entries from items when they're filtered through" +
                "\nDefault:false").define("Old cache data fixer", false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
