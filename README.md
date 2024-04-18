# Vault Filters <a href="https://www.curseforge.com/minecraft/mc-mods/vault-filters"><img src="http://cf.way2muchnoise.eu/952507.svg" alt="CF"></a>
Getting tired of sorting gear manually? This simple but powerful mod allows you to filter and sort data heavy vault gear,
trinkets, charms, inscriptions and jewels according to your needs and preferences by adding vault related attributes to
the already existing attribute filter from the Create mod. The mod adds compatibility for the list and attribute filters
with several mods including modular routers, refined storage and sophisticated backpacks. It also removes Create as a
research requirement for the filters allowing the mod to be used independently of Create using the compatibility features.
Wanna filter omega gear pieces? Put an omega item in the attribute filter and select the new "is of rarity: OMEGA" filter,
simple as that! The mod of course provides more than just rarity filtering, it provides many filter attributes!

## New Item Attributes
(Everything inside {} is the parameter of the attribute, with the contents being an example parameter.
But you can of course choose different parameters during selection in the filter menu.)


### General
- Is a {Trinket/Charm/Gear Piece/Inscription/Jewel} Item
- Is unidentified
- Is at least level {32}

### Soul value
- Has soul value
- Has exactly {256} soul value
- Has at least {128} soul value

### Affix Attributes
(Compatible with Gear Pieces and Jewels)
- Has a legendary modifier
- Has a legendary {Resistance} prefix
- Has a legendary {Trap Disarm} suffix
####
- Has a {Block Chance} implicit
- Has a {Health} prefix
- Has a {Mana Regen} suffix
####
- Has an implicit that adds at least {+5% Movement Speed}
- Has a prefix that adds at least {+10 Armor}
- Has a suffix that adds at least {+5% Item Quantity}

### Gear Pieces
- Is a {Rare} gear piece
- Has at least {2} Unused repair slots
- Uses the {Rusty Warrior} transmog

### Jewels
- Is a {Flawed} jewel
- Is a jewel with up to {22} size
- Has been free cut {2} times

### Inscriptions:
- Adds a {Wild West} room
- Adds a {Challenge} type room
####
- Adds at least {0:21}
- Adds at least {7%} completion
- Adds up to {1.1%} instability


### Charms
- Is a {Regal} charm
- Adds affinity for {Velara}
####
- Adds at least {20%} affinity
- Is a charm with at least {7} uses left

### Trinkets
- Is a {The Frog} trinket
- Is a {Blue/Red} colored trinket
- Is a trinket with at least {25} uses left

## Extra features
- The attribute and list filters from the Create mod no longer require the Create mod to be researched for crafting and use.
- The attribute filter now requires gold nuggets instead of brass nuggets to craft.

Due to these changes and the compatibility additions, create is no longer a requirement to use this mod.

## Compatibility
Thanks to [radimous](https://github.com/radimous) (rizek_ on discord), for the huge help with these

(Filters = List & Attribute Filters)

### Modular routers
- Filters now work properly inside modules. Note: Only one of each filter type can be placed, but multiple attribute filters can be placed inside a single list filter.

### Sophisticated backpacks
- Filters now function correctly as upgrades like the pickup upgrade and void upgrade.

### Refined storage
- RS filter item compatibility: Attribute filters can now be placed inside the "Filter" item.
- Filters work properly inside exporters.
- When pulling out of items stored in external storages, everything functions normally.
- For performance reasons, when pulling out of a disk, only items matching the filters will be pulled out.
- It is recommended to not make extensive use filters with exporters to avoid possible lag.

## Server / Client Requirements
- The mod functions when only on the server, allowing players without it to join. 
- Players must have the mod installed on the client to select the new attributes. 
- While not mandatory, it's recommended to install the mod on both server and clients to avoid compatibility issues.

## Disclaimer
- This mod is not a Create addon but a gear sorting addon that utilizes the existing attribute filter.
- Vault Filters is a Vault Hunters 3rd edition addon, intended for use alongside the modpack.

## Credits
Certain sections of the code are from the Create mod, which is licensed under the MIT license. See [Create's license](https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/LICENSE) for more information.