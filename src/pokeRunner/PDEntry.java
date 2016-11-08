/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokeRunner;

/**
 *
 * @author Steven Lowder
 */
public class PDEntry {

    //basic info
    public int number;
    public String spriteNum;
    public String name;
    public Typings type1;
    public Typings type2;
    public char captureType;

    //ability info
    public int pLevel;
    public Typings legendAbility;

    //evolution info
    public int evolTo;
    public boolean isBase;
    public int evolLevel;
    public boolean canEvolve;

    //stats
    public int hp;
    public int attack;
    public int defense;
    public int spAttack;
    public int spDefense;
    public int speed;

    public PDEntry() {

    }

    public boolean canCapture() {
        if (captureType == 'A' || captureType == 'B' || captureType == 'C') {
            return true;
        } else {
            return false;
        }
    }

}
