package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

import java.util.Optional;

public class BaseAbilityAffixAttribute extends AffixAttribute {
    public BaseAbilityAffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return null;
    }

    @Override
    public ItemAttribute withValue(VaultGearModifier<?> modifier) {
        if (!(modifier.getValue() instanceof AbilityLevelAttribute abiltyAttribute)) {
            return null;
        }
        String baseAbility = getBaseAbility(abiltyAttribute);

        if (baseAbility == null || baseAbility.isEmpty()) {
            return null;
        }
        return withValue(baseAbility);

    }
    public static String getBaseAbility(AbilityLevelAttribute abiltyAttribute) {

        String abilityId = abiltyAttribute.getAbility();
        Optional<Skill> ability = ModConfigs.ABILITIES.getAbilityById(abilityId);
        if (ability.isEmpty()) {
            return null;
        }
        Skill skill = ability.get();
        if (skill.getParent() == null) {
            return skill.getName();
        }
        if (skill.getParent().getName() == null) {
            return skill.getName();
        }
        return skill.getParent().getName();

    }


    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {
        if (!(modifier.getValue() instanceof AbilityLevelAttribute abiltyAttribute)) {
            return false;
        }
        return this.value.equals(getBaseAbility(abiltyAttribute));
    }

    @Override
    public String getNBTKey() {
        return "base_ability_affix";
    }
}
