/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exc;

/**
 *
 * @author Blas
 */
public class ConfException extends Exception {

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
         public String getMessage (){
              return "Items repetidos";
         }
    
}
