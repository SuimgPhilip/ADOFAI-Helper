package cn.suimg.adofai.enmus;

/**
 * 轨道对应的角度
 */
public enum Angle {

    /**
     * Left
     */
    L(0),

    /**
     *
     */
    H(30),

    /**
     *
     */
    Q(45),

    /**
     *
     */
    G(60),

    /**
     * Up
     */
    U(90),

    /**
     *
     */
    T(120),

    /**
     *
     */
    E(135),

    /**
     *
     */
    J(150),

    /**
     * Right
     */
    R(180),

    /**
     *
     */
    M(210),

    /**
     *
     */
    C(225),

    /**
     *
     */
    B(240),

    /**
     * Down
     */
    D(270),

    /**
     *
     */
    F(300),

    /**
     *
     */
    Z(315),

    /**
     *
     */
    N(330),

    /**
     * Mind Split
     */
    MID_SPLIT(-1),

    /**
     * UnKnow
     */
    UNKNOWN(-2)
    ;

    Angle(int angle){
        this.angle = angle;
    }

    private int angle;

    public int getAngle(){
        return angle;
    }

    public static Angle parseAngle(String name){
       switch (name){
           case "L":
               return L;
           case "H":
               return H;
           case "Q":
               return Q;
           case "G":
               return G;
           case "U":
               return U;
           case "T":
               return T;
           case "E":
               return E;
           case "J":
               return J;
           case "R":
               return R;
           case "M":
               return M;
           case "C":
               return C;
           case "B":
               return B;
           case "D":
               return D;
           case "F":
               return F;
           case "Z":
               return Z;
           case "N":
               return N;
           case "!":
               return MID_SPLIT;
           default:
               return UNKNOWN;
       }

    }

    public static Angle parseAngle(int angle){
        for(Angle angleObj : values()){
            if(angleObj.getAngle() == angle){
                return angleObj;
            }
        }
        return UNKNOWN;
    }


    @Override
    public String toString() {
        return this.name();
    }
}
