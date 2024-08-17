package net.joseph.vaultfilters.mixin.compat.create;


public interface FilterMenuAdvancedAccessor {
    public boolean vault_filters$getMatchAll();

    public void vault_filters$setMatchAll(boolean matchAny);
}
