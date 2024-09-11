package net.joseph.vaultfilters;

import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

public enum CreateVersion {
    CREATE_051F,
    LEGACY;

    private static CreateVersion loadedVersion;

    private static void getCreateVersion() {
        String createVersion = "";
        ModFileInfo createInfo = LoadingModList.get().getModFileById("create");
        if (createInfo != null) {
            createVersion = createInfo.versionString();
        }
        if (createVersion.contains("0.5.1.f")) { // it has suffix in dev
            loadedVersion = CREATE_051F;
        }
        if (createVersion.contains("0.5.1.b")
            || createVersion.contains("0.5.1.c")
            || createVersion.contains("0.5.1.e") ) { // it has suffix in dev
            loadedVersion = LEGACY;
        }
        if (loadedVersion == null) {
            //throw new IllegalStateException("Vault-Filters - Create version " + createVersion + " is not supported, install 0.5.1.b to 0.5.1.f");
        }
        System.out.println("Vault-Filters - Detected create: " + loadedVersion + " - " + createVersion); // logger not available so early
    }


    public static CreateVersion getLoadedVersion() {
        if (loadedVersion == null) {
            getCreateVersion();
        }
        return loadedVersion;
    }
}
