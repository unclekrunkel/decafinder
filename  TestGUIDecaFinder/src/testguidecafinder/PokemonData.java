/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testguidecafinder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rileyk64
 */
public class PokemonData {
    public Pokemon[] pokemonArray = new Pokemon[0x10000];
    
    public ArrayList<String> typeNames = new ArrayList<String>();
    // Evolution types
    public final int FRIENDSHIP_EVO = 1;
    public final int FRIENDSHIP_DAY = 2;
    public final int FRIENDSHIP_NIGHT = 3;
    public final int LEVEL_UP = 4;
    public final int TRADE_EVO = 5;
    public final int TRADE_ITEM_EVO = 6;
    public final int ITEM_EVO = 7;
    public final int ATK_GREATER_THAN_DEF = 8;
    public final int ATK_EQUALS_DEF = 9;
    public final int ATK_LESS_THAN_DEF = 10;
    public final int SILCOON_EVO = 11;
    public final int CASCOON_EVO = 12;
    public final int NINJASK_EVO = 13;
    public final int SHEDINJA_EVO = 14;
    public byte[] gameRom;
    
    // Enumeration for each game.
    public enum Game {
        EMERALD, RUBY, SAPPHIRE, FIRERED, LEAFGREEN;
    }
    // Class for characterizing a pokemons dex entry.
    public class PokedexEntry {
        ArrayList<Byte> category;
        public int height;
        public int weight;
        public int descriptionPointer;
        public int scale;
        public int offset;
        public int trainerScale;
        public int trainerOffset;
    }
    // Checks to see if a typing is stable. False = Type 1, True = Type 2
    public  boolean hasStableTyping(int species, boolean type) {
        int typing;
        if (type == false) {
            typing = pokemonArray[species].firstType;
        }
        else {
            typing = pokemonArray[species].secondType;
        }
        
        if (typing < 22) {
            return true;
        }
        else if (typing >= 31 && typing < 36) {
            return true;
        }
        else if (typing >= 59 && typing < 69) {
            return true;
        }
        else if (typing == 74) {
            return true;
        }
        else if (typing > 94 && typing < 102) {
            return true;
        }
        else if (typing == 107 || typing == 127 || typing == 134 || typing == 141 || typing == 148 || typing == 169) {
            return true;
        }
        else if (typing >186 && typing < 189) {
            return true;
        }
        else if (typing == 196 || typing == 200 || typing == 202 || typing == 223) {
            return true;
        }
        return false;
    }
    public PokedexEntry[] dexEntries = new PokedexEntry[0x10000];
    // Reads in little endian data and spits out an int.
    public int readValueFromByteArray(byte[] data, int offset, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += ((data[offset + i] & 0xFF) << (8 * i));
        }
        return sum;
    }
    // Sets all relevant data.
    public void setPokemonData(String inputFile, Game game) {
        try (
            InputStream inputStream = new FileInputStream(inputFile);
            
        ) {
            
            int speciesDataAddress;
            int speciesNamesAddress;
            int speciesAnimAddress;
            int speciesAnimDelayAddress;
            int speciesEvolutionTableAddress;
            int speciesLevelupLearnsetPointersAddress;
            int speciesTMHMLearnsetsAddress;
            int speciesEggMovesAddress;
            int speciesDexNumberAddress;
            int speciesCryAddress;
            int pokedexAddress;
            int gMonPaletteTable;
            int gMonShinyPaletteTable;
            int typeNamesAddress;
            switch (game) {
                case EMERALD:
                    speciesDataAddress = 0x3203cc;
                    speciesNamesAddress = 0x3185c8;
                    speciesAnimAddress = 0x3299ec;
                    speciesAnimDelayAddress = 0x329b87;
                    speciesEvolutionTableAddress = 0x32531c;
                    speciesLevelupLearnsetPointersAddress = 0x32937c;
                    speciesTMHMLearnsetsAddress = 0x31e898;
                    speciesEggMovesAddress = 0x32add8;
                    speciesDexNumberAddress = 0x31dc82;
                    speciesCryAddress = 0x31f61c;
                    pokedexAddress = 0x56b5b0;
                    gMonPaletteTable = 0x303678;
                    gMonShinyPaletteTable = 0x304438;
                    typeNamesAddress = 0x31AE38;
                    break;
                case RUBY:
                    // FIXME get the right addresses
                    speciesDataAddress = 0x1fec30;
                    speciesNamesAddress = 0x1f7184;
                    speciesAnimAddress = 0x000c00;
                    speciesAnimDelayAddress = 0x000c00;
                    speciesEvolutionTableAddress = 0x000c00; // need to find
                    speciesLevelupLearnsetPointersAddress = 0x000c00; // need to find
                    speciesTMHMLearnsetsAddress = 0x000c00; // need to find
                    speciesEggMovesAddress = 0x000c00; // need to find
                    speciesDexNumberAddress = 0x000c00; // need to find
                    speciesCryAddress = 0x1FDE82; 
                    pokedexAddress = 0x000c00; // need to find
                    gMonPaletteTable = 0x1ea5cc;
                    gMonShinyPaletteTable = 0x1eb38c;
                    typeNamesAddress = 0x1F9888;
                    break;
                default:
                    speciesDataAddress = 0x0;
                    speciesNamesAddress = 0x0;
                    speciesAnimAddress = 0x000000;
                    speciesAnimDelayAddress = 0x000000;
                    speciesEvolutionTableAddress = 0x000000; // need to find
                    speciesLevelupLearnsetPointersAddress = 0x000000; // need to find
                    speciesTMHMLearnsetsAddress = 0x000000; // need to find
                    speciesEggMovesAddress = 0x000000; // need to find
                    speciesDexNumberAddress = 0x000000; // need to find
                    speciesCryAddress = 0x000000; // need to find
                    pokedexAddress = 0x000000; // need to find
                    gMonPaletteTable = 0x0;
                    gMonShinyPaletteTable = 0x0;
                    typeNamesAddress = 0x0;
                    break;
            }
           
            
            
            gameRom = inputStream.readAllBytes();
            inputStream.close();
            
            InputStream rawData = new ByteArrayInputStream(gameRom);
            rawData.skip(speciesDataAddress);
            
            // Get all the stats data for each pokemon. (Stats are 28 bytes)
            for (int i = 0; i < 0x10000; i++) {
                pokemonArray[i] = new Pokemon(rawData.readNBytes(28));
            }
            rawData.close();
            int speciesID = 0;
            int j = 0;
            
            // Get all the names for each species. Set a bunch of other data while we're at it.
            while (speciesID != 65536) {
                
                ArrayList<Byte> name = new ArrayList<Byte>();
                
                // Names are supposed to just be 11 characters long, start at according offset.
                j = 11 * speciesID;
                // Keep on adding characters until a terminator byte is reached.
                while (gameRom[speciesNamesAddress + j] != (byte) 0b11111111) {
                    if (name.size() < 31) {
                       name.add(gameRom[speciesNamesAddress + j]); 
                    }
                    j++;
                }
                pokemonArray[speciesID].nameSize = j - 11 * speciesID;
                pokemonArray[speciesID].name = name;
                pokemonArray[speciesID].animationID = Byte.toUnsignedInt(gameRom[speciesAnimAddress + speciesID - 1]);
                pokemonArray[speciesID].animationDelay = Byte.toUnsignedInt(gameRom[speciesAnimDelayAddress + speciesID - 1]);
                pokemonArray[speciesID].dexNumber = readValueFromByteArray(gameRom, speciesDexNumberAddress + 2 * speciesID - 2, 2);
                pokemonArray[speciesID].learnsetPointer = readValueFromByteArray(gameRom, speciesLevelupLearnsetPointersAddress + 4 * speciesID - 4, 4);
                pokemonArray[speciesID].palettePointer = readValueFromByteArray(gameRom, gMonPaletteTable + 8 * speciesID, 4);
                pokemonArray[speciesID].paletteTag = readValueFromByteArray(gameRom, gMonPaletteTable + 8 * speciesID + 4, 2);
                pokemonArray[speciesID].shinyPalettePointer = readValueFromByteArray(gameRom, gMonShinyPaletteTable + 8 * speciesID, 4);
                pokemonArray[speciesID].shinyPaletteTag = readValueFromByteArray(gameRom, gMonShinyPaletteTable + 8 * speciesID + 4, 2);
                 speciesID++;                
            }
            
            // Set first cries up to Celebi.
            for (speciesID = 0; speciesID <= 251; speciesID++) {
                pokemonArray[speciesID].cry = speciesID;
                
            }
            // Gap of ?? species all have unown's cry.
            for (speciesID = 252; speciesID <= 276; speciesID++) {
                pokemonArray[speciesID].cry = 201;
                
            }
            // From there, game reads from array gSpeciesIdToCryId[]
            for (speciesID = 277; speciesID < 0x10000; speciesID++) {
                pokemonArray[speciesID].cry = readValueFromByteArray(gameRom, speciesCryAddress + 2 * speciesID, 2);
            }
            

            // Set all evolutions
            for (speciesID = 0; speciesID < 0x10000; speciesID++) {
                for (int evolution = 0; evolution < 5; evolution++) {
                    pokemonArray[speciesID].evolutions[evolution][0] = readValueFromByteArray(gameRom, speciesEvolutionTableAddress + 40 * speciesID + 8 * evolution, 2);
                    pokemonArray[speciesID].evolutions[evolution][1] = readValueFromByteArray(gameRom, speciesEvolutionTableAddress + 40 * speciesID + 8 * evolution + 2, 2);
                    pokemonArray[speciesID].evolutions[evolution][2] = readValueFromByteArray(gameRom, speciesEvolutionTableAddress + 40 * speciesID + 8 * evolution + 4, 2);
                    pokemonArray[speciesID].evolutions[evolution][3] = readValueFromByteArray(gameRom, speciesEvolutionTableAddress + 40 * speciesID + 8 * evolution + 8, 2);
                }
            }
            // Set all pokedex entries
            ArrayList<Byte> category;
            for (speciesID = 0; speciesID < 0x10000; speciesID++) {
                j = 0;
                category = new ArrayList<Byte>();
                while (gameRom[pokedexAddress + 0x20 * speciesID + j] != (byte) 0b11111111 && (pokedexAddress + 0x20 * speciesID + j < gameRom.length)) {
                    category.add(gameRom[pokedexAddress + 0x20 * speciesID + j]);
                    j++;
                }
                dexEntries[speciesID] = new PokedexEntry();
                dexEntries[speciesID].height = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 12, 2);
                dexEntries[speciesID].weight = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 14, 2);
                dexEntries[speciesID].descriptionPointer = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 16, 4);
                dexEntries[speciesID].scale = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 22, 2);
                dexEntries[speciesID].offset = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 24, 2);
                dexEntries[speciesID].trainerScale = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 26, 2);
                dexEntries[speciesID].trainerOffset = readValueFromByteArray(gameRom, pokedexAddress + 0x20 * speciesID + 28, 2);
                dexEntries[speciesID].category = category;
            }
            
            
            
            
            int typing = 0;
            while (typing != 18) {
                
                ArrayList<Byte> name = new ArrayList<Byte>();
                
                // Names are supposed to just be 11 characters long, start at according offset.
                j = 7 * typing;
                // Keep on adding characters until a terminator byte is reached.
                while (gameRom[typeNamesAddress + j] != (byte) 0b11111111) {
                    if (name.size() < 31) {
                       name.add(gameRom[typeNamesAddress + j]); 
                    }
                    j++;
                }
                String finalName = convertByteArrayToString(name);
                typeNames.add(finalName);
                typing++;
            }
            typeNames.add("COOL");
            typeNames.add("BEAUTY");
            typeNames.add("CUTE");
            typeNames.add("SMART");
            typeNames.add("TOUGH");
            for (int i = 23; i < 256; i++) {
                typeNames.add("Glitch " + i);
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Converts an array of bytes to a gen 3 string.
    public String convertByteArrayToString(ArrayList<Byte> array) {
        String ret = new String("");
        if (array.size() > 30) {
            for (int i = 0; i < 30; i++) {
                ret += encodeInternationalEmeraldCharacter(array.get(i));
            }
        }
        else {
            for (int i = 0; i < array.size(); i++) {
                ret += encodeInternationalEmeraldCharacter(array.get(i));
            }
        }
        return ret;
        
    }
    // Checks if an evolution is possible based on evolution type and criteria
    public boolean isValidEvolution(int species, int evolutionNumber) {
        
        int[] evolution  = pokemonArray[species].evolutions[evolutionNumber];
        // Note: [0] = evolution type
        //       [1] = type criteria (level, item, etc)
        //       [2] = species
        
        // If evolution is species 0, it won't evolve.
        if (evolution[2] == 0) {
            return false;
        }
        // Trade item evolutions always work.
        if (evolution[0] == TRADE_ITEM_EVO) {
            return true;
        }
        // Trade item evolutions always work.
        if (evolution[0] == TRADE_EVO) {
            return true;
        }
        // As long as the level is below or at level 100 it can evolve.
        if (evolution[0] == LEVEL_UP && evolution[1] <= 100) {
            return true;
        }
        // Same story with the evolution types between ATK_GREATER_THAN_DEF and SHEDINJA_EVO.
        if ((evolution[0] <= SHEDINJA_EVO && evolution[0] >= ATK_GREATER_THAN_DEF) && evolution[1] <= 100) {
            return true;
        }
        else return false;
        
    }
    // FIXME get the character encoder for Ruby
    // Character encoder for Japanese Emerald.
    public String encodeJapaneseEmeraldCharacter(byte value) {
        switch (value & 0xFF) {
         default -> {
                 return " ";
            }
        case 1 -> {
            return "あ";
            }
        case 2 -> {
            return "い";
            }
        case 3 -> {
            return "う";
            }
        case 4 -> {
            return "え";
            }
        case 5 -> {
            return "お";
            }
        case 6 -> {
            return "か";
            }
        case 7 -> {
            return "き";
            }
        case 8 -> {
            return "く";
            }
        case 9 -> {
            return "け";
            }
        case 10 -> {
            return "こ";
            }
        case 11 -> {
            return "さ";
            }
        case 12 -> {
            return "し";
            }
        case 13 -> {
            return "す";
            }
        case 14 -> {
            return "せ";
            }
        case 15 -> {
            return "そ";
            }
        case 16 -> {
            return "た";
            }
        case 17 -> {
            return "ち";
            }
        case 18 -> {
            return "つ";
            }
        case 19 -> {
            return "て";
            }
        case 20 -> {
            return "と";
            }
        case 21 -> {
            return "な";
            }
        case 22 -> {
            return "に";
            }
        case 23 -> {
            return "ぬ";
            }
        case 24 -> {
            return "ね";
            }
        case 25 -> {
            return "の";
            }
        case 26 -> {
            return "は";
            }
        case 27 -> {
            return "ひ";
            }
        case 28 -> {
            return "ふ";
            }
        case 29 -> {
            return "へ";
            }
        case 30 -> {
            return "ほ";
            }
        case 31 -> {
            return "ま";
            }
        case 32 -> {
            return "み";
            }
        case 33 -> {
            return "む";
            }
        case 34 -> {
            return "め";
            }
        case 35 -> {
            return "も";
            }
        case 36 -> {
            return "や";
            }
        case 37 -> {
            return "ゆ";
            }
        case 38 -> {
            return "よ";
            }
        case 39 -> {
            return "ら";
            }
        case 40 -> {
            return "り";
            }
        case 41 -> {
            return "る";
            }
        case 42 -> {
            return "れ";
            }
        case 43 -> {
            return "ろ";
            }
        case 44 -> {

            return "わ";
            }
        case 45 -> {
            return "を";
            }
        case 46 -> {
            return "ん";
            }
        case 47 -> {
            return "ぁ";
            }
        case 48 -> {
            return "ぃ";
            }
        case 49 -> {
            return "ぅ";
            }
        case 50 -> {
            return "ぇ";
            }
        case 51 -> {
            return "ぉ";
            }
        case 52 -> {
            return "ゃ";
            }
        case 53 -> {
            return "ゅ";
            }
        case 54 -> {
            return "ょ";
            }
        case 55 -> {
            return "が";
            }
        case 56 -> {
            return "ぎ";
            }
        case 57 -> {
            return "ぐ";
            }
        case 58 -> {
            return "げ";
            }
        case 59 -> {
            return "ご";
            }
        case 60 -> {
            return "ざ";
            }
        case 61 -> {
            return "じ";
            }
        case 62 -> {
            return "ず";
            }
        case 63 -> {
            return "ぜ";
            }
        case 64 -> {
            return "ぞ";
            }
        case 65 -> {
            return "だ";
            }
        case 66 -> {
            return "ぢ";
            }
        case 67 -> {
            return "づ";
            }
        case 68 -> {
            return "で";
            }
        case 69 -> {
            return "ど";
            }
        case 70 -> {
            return "ば";
            }
        case 71 -> {
            return "び";
            }
        case 72 -> {
            return "ぶ";
            }
        case 73 -> {
            return "べ";
            }
        case 74 -> {
            return "ぼ";
            }
        case 75 -> {
            return "ぱ";
            }
        case 76 -> {
            return "ぴ";
            }
        case 77 -> {
            return "ぷ";
            }
        case 78 -> {
            return "ぺ";
            }
        case 79 -> {
            return "ぽ";
            }
        case 80 -> {
            return "っ";
            }
        case 81 -> {
            return "ア";
            }
        case 82 -> {
            return "イ";
            }
        case 83 -> {
            return "ウ";
            }
        case 84 -> {
            return "エ";
            }
        case 85 -> {
            return "オ";
            }
        case 86 -> {
            return "カ";
            }
        case 87 -> {
            return "キ";
            }
        case 88 -> {
            return "ク";
            }
        case 89 -> {
            return "ケ";
            }
        case 90 -> {
            return "コ";
            }
        case 91 -> {
            return "サ";
            }
        case 92 -> {
            return "シ";
            }
        case 93 -> {
            return "ス";
            }
        case 94 -> {
            return "セ";
            }
        case 95 -> {
            return "ソ";
            }
        case 96 -> {
            return "タ";
            }
        case 97 -> {
            return "チ";
            }
        case 98 -> {
            return "ツ";
            }
        case 99 -> {
            return "テ";
            }
        case 100 -> {
            return "ト";
            }
        case 101 -> {
            return "ナ";
            }
        case 102 -> {
            return "ニ";
            }
        case 103 -> {
            return "ヌ";
            }
        case 104 -> {
            return "ネ";
            }
        case 105 -> {
            return "ノ";
            }
        case 106 -> {
            return "ハ";
            }
        case 107 -> {
            return "ヒ";
            }
        case 108 -> {
            return "フ";
            }
        case 109 -> {
            return "ヘ";
            }
        case 110 -> {
            return "ホ";
            }
        case 111 -> {
            return "マ";
            }
        case 112 -> {
            return "ミ";
            }
        case 113 -> {
            return "ム";
            }
        case 114 -> {
            return "メ";
            }
        case 115 -> {
            return "モ";
            }
        case 116 -> {
            return "ヤ";
            }
        case 117 -> {
            return "ユ";
            }
        case 118 -> {
            return "ヨ";
            }
        case 119 -> {
            return "ラ";
            }
        case 120 -> {
            return "リ";
            }
        case 121 -> {
            return "ル";
            }
        case 122 -> {
            return "レ";
            }
        case 123 -> {
            return "ロ";
            }
        case 124 -> {
            return "ワ";
            }
        case 125 -> {
            return "ヲ";
            }
        case 126 -> {
            return "ン";
            }
        case 127 -> {
            return "ァ";
            }
        case 128 -> {
            return "ィ";
            }
        case 129 -> {
            return "ゥ";
            }
        case 130 -> {

            return "ェ";
            }
        case 131 -> {
            return "ォ";
            }
        case 132 -> {
            return "ャ";
            }
        case 133 -> {
            return "ュ";
            }
        case 134 -> {
            return "ョ";
            }
        case 135 -> {
            return "ガ";
            }
        case 136 -> {
            return "ギ";
            }
        case 137 -> {
            return "グ";
            }
        case 138 -> {
            return "ゲ";
            }
        case 139 -> {
            return "ゴ";
            }
        case 140 -> {
            return "ザ";
            }
        case 141 -> {
            return "ジ";
            }
        case 142 -> {
            return "ズ";
            }
        case 143 -> {
            return "ゼ";
            }
        case 144 -> {
            return "ゾ";
            }
        case 145 -> {
            return "ダ";
            }
        case 146 -> {
            return "ヂ";
            }
        case 147 -> {
            return "ヅ";
            }
        case 148 -> {
            return "デ";
            }
        case 149 -> {
            return "ド";
            }
        case 150 -> {
            return "バ";
            }
        case 151 -> {
            return "ビ";
            }
        case 152 -> {
            return "ブ";
            }
        case 153 -> {
            return "ベ";
            }
        case 179 -> {
            return "‘";
            }
        case 180 -> {
            return "’";
            }
        case 181 -> {
            return "♂";
            }
        case 182 -> {
            return "♀";
            }
        case 183 -> {
            return "$";
            }
        case 184 -> {
            return ",";
            }
        case 185 -> {
            return "x";
            }
        case 186 -> {
            return "/";
            }
        case 187 -> {
            return "A";
            }
        case 188 -> {
            return "B";
            }
        case 189 -> {
            return "C";
            }
        case 190 -> {
            return "D";
            }
        case 191 -> {
            return "E";
            }
        case 192 -> {
            return "F";
            }
        case 193 -> {
            return "G";
            }
        case 194 -> {
            return "H";
            }
        case 195 -> {
            return "I";
            }
        case 196 -> {
            return "J";
            }
        case 197 -> {
            return "K";
            }
        case 198 -> {
            return "L";
            }
        case 199 -> {
            return "M";
            }
        case 200 -> {
            return "N";
            }
        case 201 -> {
            return "O";
            }
        case 202 -> {
            return "P";
            }
        case 203 -> {
            return "Q";
            }
        case 204 -> {
            return "R";
            }
        case 205 -> {
            return "S";
            }
        case 206 -> {
            return "T";
            }
        case 207 -> {
            return "U";
            }
        case 208 -> {
            return "V";
            }
        case 209 -> {
            return "W";
            }
        case 210 -> {
            return "X";
            }
        case 211 -> {
            return "Y";
            }
        case 212 -> {
            return "Z";
            }
        case 213 -> {
            return "a";
            }
        case 214 -> {
            return "b";
            }
        case 215 -> {
            return "c";
            }
        case 216 -> {
            return "d";
            }
        case 217 -> {
            return "e";
            }
        case 218 -> {
            return "f";
            }
        case 219 -> {
            return "g";
            }
        case 220 -> {
            return "h";
            }
        case 221 -> {
            return "i";
            }
        case 222 -> {
            return "j";
            }
        case 223 -> {
            return "k";
            }
        case 224 -> {
            return "l";
            }
        case 225 -> {
            return "m";
            }
        case 226 -> {
            return "n";
            }
        case 227 -> {
            return "o";
            }
        case 228 -> {
            return "p";
            }
        case 229 -> {
            return "q";
            }
        case 230 -> {
            return "r";
            }
        case 231 -> {
            return "s";
            }
        case 232 -> {
            return "t";
            }
        case 233 -> {
            return "u";
            }
        case 234 -> {
            return "v";
            }
        case 235 -> {
            return "w";
            }
        case 236 -> {
            return "x";
            }
        case 237 -> {
            return "y";
            }
        case 238 -> {
            return "z";
            }
        case 239 -> {
            return "▶";
            }
        case 240 -> {
            return ":";
            }
        case 241 -> {
            return "Ä";
            }
        case 242 -> {
            return "Ö";
            }
        case 243 -> {
            return "Ü";
            }
        case 244 -> {
            return "ä";
            }
        case 245 -> {
            return "ö";
            }
        case 246 -> {
            return "ü";
            }
        case 250 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFA";
            }
        case 251 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFB";
            }
        case 252 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFC";
            }
        case 253 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFD";
            }
        case 254 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFE";
            }
        case 255 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFF";
            }
        }
    }
    // Character encoder for international versions of Emerald.
    public String encodeInternationalEmeraldCharacter(byte value) {
        switch (value & 0xFF) {
         default -> {
                 return " ";
            }
        case 1 -> {
            return "À";
            }
        case 2 -> {
            return "Á";
            }
        case 3 -> {
            return "Â";
            }
        case 4 -> {
            return "Ç";
            }
        case 5 -> {
            return "È";
            }
        case 6 -> {
            return "É";
            }
        case 7 -> {
            return "Ê";
            }
        case 8 -> {
            return "Ë";
            }
        case 9 -> {
            return "Ì";
            }
        case 10 -> {
            return " ";
            }
        case 11 -> {
            return "Î";
            }
        case 12 -> {
            return "Ï";
            }
        case 13 -> {
            return "Ò";
            }
        case 14 -> {
            return "Ó";
            }
        case 15 -> {
            return "Ô";
            }
        case 16 -> {
            return "Œ";
            }
        case 17 -> {
            return "Ù";
            }
        case 18 -> {
            return "Ú";
            }
        case 19 -> {
            return "Û";
            }
        case 20 -> {
            return "Ñ";
            }
        case 21 -> {
            return "ß";
            }
        case 22 -> {
            return "à";
            }
        case 23 -> {
            return "á";
            }
        case 25 -> {
            return "ç";
            }
        case 26 -> {
            return "è";
            }
        case 27 -> {
            return "é";
            }
        case 28 -> {
            return "ê";
            }
        case 29 -> {
            return "ë";
            }
        case 30 -> {
            return "ì";
            }
        case 32 -> {
            return "î";
            }
        case 33 -> {
            return "ï";
            }
        case 34 -> {
            return "ò";
            }
        case 35 -> {
            return "ó";
            }
        case 36 -> {
            return "ô";
            }
        case 37 -> {
            return "œ";
            }
        case 38 -> {
            return "ù";
            }
        case 39 -> {
            return "ú";
            }
        case 40 -> {
            return "û";
            }
        case 41 -> {
            return "ñ";
            }
        case 42 -> {
            return "º";
            }
        case 43 -> {
            return "ª";
            }
        case 44 -> {
            //Shortened because its two characters.
            return "ᵉʳ";
            }
        case 45 -> {
            return "&";
            }
        case 46 -> {
            return "+";
            }
        case 52 -> {
            return "Lv";
            }
        case 53 -> {
            return "=";
            }
        case 54 -> {
            return ";";
            }
        case 72 -> {
            return "(0x48)";
            }
        case 73 -> {
            return "(0x49)";
            }
        case 74 -> {
            return "(0x4A)";
            }
        case 75 -> {
            return "(0x4B)";
            }
        case 76 -> {
            return "(0x4C)";
            }
        case 77 -> {
            return "(0x4D)";
            }
        case 78 -> {
            return "(0x4E)";
            }
        case 79 -> {
            return "(0x4F)";
            }
        case 80 -> {
            return "▯";
            }
        case 81 -> {
            return "¿";
            }
        case 82 -> {
            return "¡";
            }
        case 83 -> {
            return "PK";
            }
        case 84 -> {
            return "MN";
            }
        case 85 -> {
            return "PO";
            }
        case 86 -> {
            return "Ké";
            }
        case 87 -> {
            return "BL";
            }
        case 88 -> {
            return "OC";
            }
        case 89 -> {
            return "K";
            }
        case 90 -> {
            return "Í";
            }
        case 91 -> {
            return "%";
            }
        case 92 -> {
            return "(";
            }
        case 93 -> {
            return ")";
            }
        case 104 -> {
            return "â";
            }
        case 111 -> {
            return "í";
            }
        case 121 -> {
            return "⬆";
            }
        case 122 -> {
            return "⬇";
            }
        case 123 -> {
            return "⬅";
            }
        case 124 -> {
            return "➡";
            }
        case 125 -> {
            return " ";
            }
        case 126 -> {
            return "  ";
            }
        case 127 -> {
            return "   ";
            }
        case 128 -> {
            return "    ";
            }
        case 129 -> {
            return "     ";
            }
        case 130 -> {

            return "      ";
            }
        case 131 -> {
            return "       ";
            }
        
        case 132 -> {
            return "ᵉ";
            }
        case 133 -> {
            return "<";
            }
        case 134 -> {
            return ">";
            }
      
        
        case 160 -> {
            return "ʳᵉ";
            }
        case 161 -> {
            return "0";
            }
        case 162 -> {
            return "1";
            }
        case 163 -> {
            return "2";
            }
        case 164 -> {
            return "3";
            }
        case 165 -> {
            return "4";
            }
        case 166 -> {
            return "5";
            }
        case 167 -> {
            return "6";
            }
        case 168 -> {
            return "7";
            }
        case 169 -> {
            return "8";
            }
        case 170 -> {
            return "9";
            }
        case 171 -> {
            return "!";
            }
        case 172 -> {
            return "?";
            }
        case 173 -> {
            return ".";
            }
        case 174 -> {
            return "-";
            }
        case 175 -> {
            return "・";
            }
        case 176 -> {
            return ".";
            }
        case 177 -> {
            return "“";
            }
        case 178 -> {
            return "”";
            }
        case 179 -> {
            return "‘";
            }
        case 180 -> {
            return "’";
            }
        case 181 -> {
            return "♂";
            }
        case 182 -> {
            return "♀";
            }
        case 183 -> {
            return "$";
            }
        case 184 -> {
            return ",";
            }
        case 185 -> {
            return "x";
            }
        case 186 -> {
            return "/";
            }
        case 187 -> {
            return "A";
            }
        case 188 -> {
            return "B";
            }
        case 189 -> {
            return "C";
            }
        case 190 -> {
            return "D";
            }
        case 191 -> {
            return "E";
            }
        case 192 -> {
            return "F";
            }
        case 193 -> {
            return "G";
            }
        case 194 -> {
            return "H";
            }
        case 195 -> {
            return "I";
            }
        case 196 -> {
            return "J";
            }
        case 197 -> {
            return "K";
            }
        case 198 -> {
            return "L";
            }
        case 199 -> {
            return "M";
            }
        case 200 -> {
            return "N";
            }
        case 201 -> {
            return "O";
            }
        case 202 -> {
            return "P";
            }
        case 203 -> {
            return "Q";
            }
        case 204 -> {
            return "R";
            }
        case 205 -> {
            return "S";
            }
        case 206 -> {
            return "T";
            }
        case 207 -> {
            return "U";
            }
        case 208 -> {
            return "V";
            }
        case 209 -> {
            return "W";
            }
        case 210 -> {
            return "X";
            }
        case 211 -> {
            return "Y";
            }
        case 212 -> {
            return "Z";
            }
        case 213 -> {
            return "a";
            }
        case 214 -> {
            return "b";
            }
        case 215 -> {
            return "c";
            }
        case 216 -> {
            return "d";
            }
        case 217 -> {
            return "e";
            }
        case 218 -> {
            return "f";
            }
        case 219 -> {
            return "g";
            }
        case 220 -> {
            return "h";
            }
        case 221 -> {
            return "i";
            }
        case 222 -> {
            return "j";
            }
        case 223 -> {
            return "k";
            }
        case 224 -> {
            return "l";
            }
        case 225 -> {
            return "m";
            }
        case 226 -> {
            return "n";
            }
        case 227 -> {
            return "o";
            }
        case 228 -> {
            return "p";
            }
        case 229 -> {
            return "q";
            }
        case 230 -> {
            return "r";
            }
        case 231 -> {
            return "s";
            }
        case 232 -> {
            return "t";
            }
        case 233 -> {
            return "u";
            }
        case 234 -> {
            return "v";
            }
        case 235 -> {
            return "w";
            }
        case 236 -> {
            return "x";
            }
        case 237 -> {
            return "y";
            }
        case 238 -> {
            return "z";
            }
        case 239 -> {
            return "▶";
            }
        case 240 -> {
            return ":";
            }
        case 241 -> {
            return "Ä";
            }
        case 242 -> {
            return "Ö";
            }
        case 243 -> {
            return "Ü";
            }
        case 244 -> {
            return "ä";
            }
        case 245 -> {
            return "ö";
            }
        case 246 -> {
            return "ü";
            }
        case 250 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFA";
            }
        case 251 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFB";
            }
        case 252 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFC";
            }
        case 253 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFD";
            }
        case 254 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFE";
            }
        case 255 -> {
            // Each of these is a special control character, effects would be too hard to implement.
            return "0xFF";
            }
        }
    }   
    // Gives detailed description of a pokemon.
    public void printPokemonDataDetailed(int species) {
        Pokemon pokemon = pokemonArray[species];
        System.out.println("****************************************************");
        System.out.println("Species  : " + species);
        System.out.println("Name     : " + convertByteArrayToString(pokemon.name));
        System.out.println("Type 1   : " + typeNames.get(pokemon.firstType));
        System.out.println("Type 2   : " + typeNames.get(pokemon.secondType));
        System.out.println("Ability 1: " + pokemon.firstAbility);
        System.out.println("Ability 2: " + pokemon.secondAbility);
        System.out.println("   HP      - " + pokemon.HP);
        System.out.println("   Attack  - " + pokemon.attack);
        System.out.println("   Defense - " + pokemon.defense);
        System.out.println("   SpA     - " + pokemon.spA);
        System.out.println("   SpD     - " + pokemon.spD);
        System.out.println("   Speed   - " + pokemon.speed);
        System.out.print("Palette Pointer: " + String.format("0x%08X", pokemon.palettePointer));
        // If the palette pointer is within ROM, display its size.
        if (pokemon.palettePointer >= 0x8000000 && pokemon.palettePointer <= (0x9000000 - 4)) {
            int paletteSize = 0;
            int pointer = pokemon.palettePointer - 0x8000000;
            if (pointer % 2 == 0 ) {
                paletteSize = readValueFromByteArray(gameRom, pointer + 1, 3);
            }
            if (pointer % 2 == 1) {
                paletteSize = Byte.toUnsignedInt(gameRom[pointer + 1]) + 0x100 * Byte.toUnsignedInt(gameRom[pointer + 2]) + 0x10000 * Byte.toUnsignedInt(gameRom[pointer - 1]);
            }          
            System.out.print( " - Palette Size: " + String.format("0x%06X",paletteSize));
        }
        System.out.println("\r\nPalette Tag: " + String.format("0x%04X", pokemon.paletteTag));
        
    }
    // Checks if palette is below 0x2000000, or if the palette is within ROM and has an acceptable size.
    public boolean isPaletteStable(int i) {
        
        if (Integer.toUnsignedLong(pokemonArray[i].palettePointer) < 0x2000000) {
            return true;
        }
        if (pokemonArray[i].palettePointer >= 0x8000000 && pokemonArray[i].palettePointer <= (0x9000000 - 4)) {
            int paletteSize = 0;
            int pointer = pokemonArray[i].palettePointer - 0x8000000;
            if (pointer % 2 == 0 ) {
                paletteSize = readValueFromByteArray(gameRom, pointer + 1, 3);
            }
            if (pointer % 2 == 1) {
                paletteSize = Byte.toUnsignedInt(gameRom[pointer + 1]) + 0x100 * Byte.toUnsignedInt(gameRom[pointer + 2]) + 0x10000 * Byte.toUnsignedInt(gameRom[pointer - 1]);
            }
            if (paletteSize < 0x10000) {
                return true;
            }
        }
        return false;
        
    }
    class EvoNode {
            public int species;
            public ArrayList<EvoNode> evolutions;
            public EvoNode(int specie) {
                species = specie;
                evolutions = new ArrayList<EvoNode>();
            }
        }
    public void evolutionTesting() {
        
        EvoNode[] allSpecies = new EvoNode[0x10000];
        for (int i = 0; i < 0x10000; i++) {
            allSpecies[i] = new EvoNode(i); 
        }
        for (int i = 0; i < 0x10000; i++) {
//            if (!(pokemonArray[i].animationID < 151)) {
//                continue;
//            }
            if (!isPaletteStable(i)) {
                continue;
            }
            for (int j = 0; j < 5; j++) {
                if (isValidEvolution(i, j) && (pokemonArray[i].evolutions[j][2] > 412)) {
                    if (isPaletteStable(pokemonArray[i].evolutions[j][2])){
                        if (isMonLearnsetStable(pokemonArray[i].evolutions[j][2])) {
                            allSpecies[i].evolutions.add(allSpecies[pokemonArray[i].evolutions[j][2]]);
                        }
                    }    
                }
            }
        }
        int maxEvoChain = 0;
        int maxEvoSpecies = 0;
        for (int i = 0; i < 0x10000; i++) {
            int x = depthOfTree(allSpecies[i]);
            if (x > maxEvoChain ) {
                maxEvoChain = x;
                maxEvoSpecies = i;
            }
        }
        System.out.println("Species: " + maxEvoSpecies + " Evolutions: " + maxEvoChain);
        System.out.println(String.format("0x%08X", pokemonArray[52808].learnsetPointer));
        
    }
    int depthOfTree(EvoNode node) {
            if (node.evolutions.isEmpty()) {
                return 0;
            }
            int maxdepth = 0;
            for (EvoNode evo : node.evolutions) {
                maxdepth = Math.max(maxdepth, depthOfTree(evo));
                
            }
            return maxdepth + 2;
    }
    public int getLearnsetSize(int species) {
        int pointer = pokemonArray[species].learnsetPointer - 0x8000000;
        int size = 0;
        while (pointer < 0x1000000) {
            if (readValueFromByteArray(gameRom, pointer, 2) == 0xFFFF) {
                return size;
            }
            size++;
            pointer += 2;
        }
        return 256;
    }
    public boolean isMonLearnsetStable(int species) {
        Pokemon pokemon = pokemonArray[species];
        
        if (pokemon.learnsetPointer >= 0x8000000 && pokemon.learnsetPointer < 0x9000000) {
            if (getLearnsetSize(species) < 256) {
                return true;
            }
        }
        return false;
    }
    public boolean isMonSummaryStable(int species, Game game) {
        switch (game) {
            case EMERALD:
                if (hasStableTyping(species, false) && hasStableTyping(species, true)) {
                    if (pokemonArray[species].paletteTag < 440 || (pokemonArray[species].paletteTag >= 500 && pokemonArray[species].paletteTag < 940))
                        if (pokemonArray[species].animationID < 150)
                            if (pokemonArray[species].nameSize < 15) 
                                if (isPaletteStable(species)) {
                                    return true;
                                }
                            
                }
                else return false;
                break;
            case RUBY:
                if (isPaletteStable(species)) {
                    if (hasStableTyping(species, false) && hasStableTyping(species, true)) { // Need to check what typings are stable
                        if (pokemonArray[species].firstAbility < 78) { // Needs to be fixed, check what abilities are stable, etc
                            return true;
                        }
                    }
                }
                else return false;
                break;
            default:
                return true;
        }
        return false;
    }
    public void testForCustomSprites() {
        for (int i = 0; i < 0x10000; i++) {
            int x = pokemonArray[i].palettePointer & 0xFF03FFFF;
            if (x >= 0x20300a0 && x <= 0x2038070) {
                System.out.println("Species: " + i + " Palette Pointer: " + String.format("%08X", x) + "  Actual: " + String.format("%08X", pokemonArray[i].palettePointer) + " name size: " + pokemonArray[i].nameSize);
            }
        }
    }
    public int getPaletteSize(int species) {
        
        int paletteSize = 0;
            if (pokemonArray[species].palettePointer >= 0x8000000 && pokemonArray[species].palettePointer < 0x9000000 - 4) {
            int pointer = pokemonArray[species].palettePointer - 0x8000000;
            if (pointer % 2 == 0 ) {
                paletteSize = readValueFromByteArray(gameRom, pointer + 1, 3);
            }
            if (pointer % 2 == 1) {
                paletteSize = Byte.toUnsignedInt(gameRom[pointer + 1]) + 0x100 * Byte.toUnsignedInt(gameRom[pointer + 2]) + 0x10000 * Byte.toUnsignedInt(gameRom[pointer - 1]);
            }          
            }
            return paletteSize;
    }
    public static void main(String args[]) {
        PokemonData data = new PokemonData();
        data.setPokemonData("src/data/test.gba", Game.EMERALD);
        
        // data.testForCustomSprites();
        for (int i = 412; i < 65536; i++) {
            if (data.pokemonArray[i].dexNumber == 0x1a3)
            System.out.println("Species: " + i + " " + String.format("%08X", data.pokemonArray[i].palettePointer) + " " + String.format("%06X",data.getPaletteSize(i)) + " Name: " +  " " + data.convertByteArrayToString(data.pokemonArray[i].name) + "(Size: " + data.pokemonArray[i].nameSize + ")" + " " + data.pokemonArray[i].animationID);
        }
        System.out.println(data.pokemonArray[0x40e9].dexNumber);
    }
}
  


