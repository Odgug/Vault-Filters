package net.joseph.vaultfilters;

import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

public enum CreateVersion {
    CREATE_051F,
    LEGACY;

    private static CreateVersion loadedVersion;

    private static CreateVersion getCreateVersion() {
        String createVersion = "";
        ModFileInfo createInfo = LoadingModList.get().getModFileById("create");
        if (createInfo != null) {
            createVersion = createInfo.versionString();
        }

        if (createVersion.contains("0.5.1.f")
            || createVersion.contains("0.5.1.g")
            || createVersion.contains("0.5.1.h")
            || createVersion.contains("0.5.1.i")
        ) { // it has suffix in dev
            loadedVersion = CREATE_051F;
        } else if (createVersion.contains("0.5.1.b")
            || createVersion.contains("0.5.1.c")
            || createVersion.contains("0.5.1.e") ) { // it has suffix in dev
            loadedVersion = LEGACY;
        }

        System.out.println("Vault-Filters - Detected create: " + loadedVersion + " - " + createVersion); // logger not available so early
        return loadedVersion;
    }


    public static CreateVersion getLoadedVersion() {
        return loadedVersion == null
                ? getCreateVersion()
                : loadedVersion;
    }
}
