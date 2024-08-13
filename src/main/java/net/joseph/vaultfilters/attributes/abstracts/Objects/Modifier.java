package net.joseph.vaultfilters.attributes.abstracts.Objects;

public class Modifier {
    protected Number level;
    protected String displayName;
    protected String normalName;
    public Modifier(String displayName, String normalName, Number level) {
        this.displayName = displayName;
        this.normalName = normalName;
        this.level = level;
    }
    public String getDisplayName() {
        return this.displayName;
    }
    public String getNormalName() {
        return this.normalName;
    }
    public Number getLevel() {
        return this.level;
    }
}
