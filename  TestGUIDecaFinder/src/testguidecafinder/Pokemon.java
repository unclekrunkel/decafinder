/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testguidecafinder;

import java.util.ArrayList;

/**
 *
 * @author Drew Kunkel
 */

public class Pokemon {
    
    public int expYield;
    public int expCurve;
    public int gender;
    public int index;
    public int HP;
    public int attack;
    public int defense;
    public int speed;
    public int spA;
    public int spD;
    public int firstType;
    public int secondType;
    public int catchRate;
    public int effortYield;
    public int firstWildItem;
    public int secondWildItem;
    public int eggCycles;
    public int baseFriendship;
    public int firstEggGroup;
    public int secondEggGroup;
    public int firstAbility;
    public int secondAbility;
    public int safariRate;
    public int colorAndFlip;
    public int padding;
    
    public ArrayList<Byte> name;
    public int nameSize;
    public int animationID;
    public int animationDelay;
    public int dexNumber;
    public int cry;
    
    public int[][] evolutions = new int[5][4];
    public int learnsetPointer;
    
    public int palettePointer;
    public int paletteTag;
    public int shinyPalettePointer;
    public int shinyPaletteTag;

Pokemon(byte[] rawData) {
    // Source:
    // https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_species_data_structure_(Generation_III)
    
    HP = Byte.toUnsignedInt(rawData[0]);
    attack = Byte.toUnsignedInt(rawData[1]);
    defense = Byte.toUnsignedInt(rawData[2]);
    speed = Byte.toUnsignedInt(rawData[3]);
    spA = Byte.toUnsignedInt(rawData[4]);
    spD = Byte.toUnsignedInt(rawData[5]);
    firstType = Byte.toUnsignedInt(rawData[6]);
    secondType = Byte.toUnsignedInt(rawData[7]);
    catchRate = Byte.toUnsignedInt(rawData[8]);
    expYield = Byte.toUnsignedInt(rawData[9]);
    effortYield = 0x100 * Byte.toUnsignedInt(rawData[11]) + Byte.toUnsignedInt(rawData[10]);
    firstWildItem = 0x100 * Byte.toUnsignedInt(rawData[13]) + Byte.toUnsignedInt(rawData[12]);
    secondWildItem = 0x100 * Byte.toUnsignedInt(rawData[15]) + Byte.toUnsignedInt(rawData[14]);
    gender = Byte.toUnsignedInt(rawData[16]);
    eggCycles = Byte.toUnsignedInt(rawData[17]);
    baseFriendship = Byte.toUnsignedInt(rawData[18]);
    expCurve = Byte.toUnsignedInt(rawData[19]);
    firstEggGroup = Byte.toUnsignedInt(rawData[20]);
    secondEggGroup = Byte.toUnsignedInt(rawData[21]);
    firstAbility = Byte.toUnsignedInt(rawData[22]);
    secondAbility = Byte.toUnsignedInt(rawData[23]);
    safariRate = Byte.toUnsignedInt(rawData[24]);
    colorAndFlip = Byte.toUnsignedInt(rawData[25]);
    padding = 0x100 * Byte.toUnsignedInt(rawData[27]) + Byte.toUnsignedInt(rawData[26]);

}

// Gets egg group name from egg group index.
String getEggGroupName(boolean whichGroup) {
    int eggGroup;

    if (whichGroup) {
            eggGroup = firstEggGroup;
    }
    else {
            eggGroup = secondEggGroup;
    }
        return switch (eggGroup) {
            case 1 -> "Monster";
            case 2 -> "Water 1";
            case 3 -> "Bug";
            case 4 -> "Flying";
            case 5 -> "Field";
            case 6 -> "Fairy";
            case 7 -> "Grass";
            case 8 -> "Human-Like";
            case 9 -> "Water 3";
            case 10 -> "Mineral";
            case 11 -> "Amorphous";
            case 12 -> "Water 2";
            case 13 -> "Ditto";
            case 14 -> "Dragon";
            case 15 -> "Undiscovered";
            default -> "Glitch " + eggGroup;
        };
}

String getCurveName() {
        return switch (expCurve) {
            case 0 -> "Medium Fast";
            case 1 -> "Erratic";
            case 2 -> "Fluctuating";
            case 3 -> "Medium Slow";
            case 4 -> "Fast";
            case 5 -> "Slow";
            default -> "Glitch " + expCurve;
        };
}
    
}

