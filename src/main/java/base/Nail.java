package base;

import java.io.Serializable;

/**
 * Created by nigga on 09.11.15.
 */
public class Nail  implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private int health;
    public Nail(){
        health = 10;
    }
    public int getHealth(){ return health;}
}
