/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

/**
 *
 * @author admin
 */

public class NoCardInsertedException extends Exception {
    public NoCardInsertedException(String message) {
        super(message);
    }
}