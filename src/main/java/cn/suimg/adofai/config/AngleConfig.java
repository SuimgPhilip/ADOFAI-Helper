package cn.suimg.adofai.config;

import cn.suimg.adofai.enmus.Angle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.suimg.adofai.enmus.Angle.*;

/**
 * 角度的配置
 */
public final class AngleConfig {

    /**
     * 角度对应表
     */
    public static final Map<Integer, List<Angle[]>> angleMapping = new HashMap<Integer,List<Angle[]>>(){{

        /**
         * 所有15°角 顺时针方向
         */
        put(15,Arrays.asList(
                new Angle[]{H,Q},
                new Angle[]{Q,G},

                new Angle[]{T,E},
                new Angle[]{E,J},

                new Angle[]{M,C},
                new Angle[]{C,B},

                new Angle[]{F,Z},
                new Angle[]{Z,N}
        ));

        /**
         * 所有30度角 顺时针方向
         */
        put(30, Arrays.asList(
                new Angle[]{L,H},
                new Angle[]{H,G},
                new Angle[]{G,U},

                new Angle[]{U,T},
                new Angle[]{T,J},
                new Angle[]{J,R},

                new Angle[]{R,M},
                new Angle[]{M,B},
                new Angle[]{B,D},

                new Angle[]{D,F},
                new Angle[]{F,N},
                new Angle[]{N,L}
        ));

        /**
         * 所有45°角 顺时针方向
         */
        put(45,Arrays.asList(
                new Angle[]{L,Q},
                new Angle[]{Q,U},

                new Angle[]{U,E},
                new Angle[]{E,R},

                new Angle[]{R,C},
                new Angle[]{C,D},

                new Angle[]{D,Z},
                new Angle[]{Z,L}
        ));

        /**
         * 所有60°角 顺时针方向
         */
        put(60,Arrays.asList(
                new Angle[]{L,G},
                new Angle[]{H,U},
                new Angle[]{G,T},

                new Angle[]{U,J},
                new Angle[]{T,R},
                new Angle[]{J,M},

                new Angle[]{R,B},
                new Angle[]{M,D},
                new Angle[]{B,F},

                new Angle[]{D,N},
                new Angle[]{F,L},
                new Angle[]{N,H}
        ));

        /**
         * 所有75°角 顺时针方向
         */
        put(75,Arrays.asList(
                new Angle[]{Q,T},
                new Angle[]{G,E},

                new Angle[]{E,M},
                new Angle[]{J,C},

                new Angle[]{C,F},
                new Angle[]{B,Z},

                new Angle[]{Z,H},
                new Angle[]{N,Q}
        ));

        /**
         * 所有90°角 顺时针方向
         */
        put(90,Arrays.asList(
                new Angle[]{L,U},
                new Angle[]{H,T},
                new Angle[]{Q,E},
                new Angle[]{G,J},

                new Angle[]{U,R},
                new Angle[]{T,M},
                new Angle[]{E,C},
                new Angle[]{J,B},

                new Angle[]{R,D},
                new Angle[]{M,F},
                new Angle[]{C,Z},
                new Angle[]{B,N},

                new Angle[]{D,L},
                new Angle[]{F,H},
                new Angle[]{Z,Q},
                new Angle[]{N,G}

        ));

        put(105,Arrays.asList(
           new Angle[]{H,E},
           new Angle[]{Q,J},

           new Angle[]{T,C},
           new Angle[]{E,B},

           new Angle[]{M,Z},
           new Angle[]{C,N},

           new Angle[]{F,Q},
           new Angle[]{Z,G}

        ));

        /**
         * 所有120°角 顺时针方向
         */
        put(120,Arrays.asList(
                new Angle[]{L,T},
                new Angle[]{H,J},
                new Angle[]{G,R},

                new Angle[]{U,M},
                new Angle[]{T,B},
                new Angle[]{J,D},

                new Angle[]{R,F},
                new Angle[]{M,N},
                new Angle[]{B,L},

                new Angle[]{D,H},
                new Angle[]{F,G},
                new Angle[]{N,U}
        ));

        /**
         * 所有135°角 顺时针方向
         */
        put(135,Arrays.asList(
                new Angle[]{L,E},
                new Angle[]{Q,R},

                new Angle[]{U,C},
                new Angle[]{E,D},

                new Angle[]{R,Z},
                new Angle[]{C,L},

                new Angle[]{D,Q},
                new Angle[]{Z,U}
        ));


        /**
         * 所有150°角 顺时针方向
         */
        put(150,Arrays.asList(
                new Angle[]{L,J},
                new Angle[]{H,R},
                new Angle[]{G,M},

                new Angle[]{U,B},
                new Angle[]{T,D},
                new Angle[]{J,F},

                new Angle[]{R,N},
                new Angle[]{M,L},
                new Angle[]{B,H},

                new Angle[]{D,G},
                new Angle[]{F,U},
                new Angle[]{N,T}

        ));

        /**
         * 所有165°的角 顺时针方向
         */
        put(165,Arrays.asList(
                new Angle[]{Q,M},
                new Angle[]{G,C},

                new Angle[]{E,F},
                new Angle[]{J,Z},

                new Angle[]{C,H},
                new Angle[]{B,Q},

                new Angle[]{Z,T},
                new Angle[]{N,E}
        ));

        /**
         * 所有180°角 顺时针方向
         */
        put(180,Arrays.asList(
                new Angle[]{L,R},
                new Angle[]{H,M},
                new Angle[]{Q,C},
                new Angle[]{G,B},

                new Angle[]{U,D},
                new Angle[]{T,F},
                new Angle[]{E,Z},
                new Angle[]{J,N},

                new Angle[]{R,L},
                new Angle[]{M,H},
                new Angle[]{C,Q},
                new Angle[]{B,G},

                new Angle[]{D,U},
                new Angle[]{F,T},
                new Angle[]{Z,E},
                new Angle[]{N,J}
        ));

        /**
         * 所有360°角 顺时针方向
         */
        put(360,Arrays.asList(
                new Angle[]{L,L},
                new Angle[]{H,H},
                new Angle[]{Q,Q},
                new Angle[]{G,G},

                new Angle[]{U,U},
                new Angle[]{T,T},
                new Angle[]{E,E},
                new Angle[]{J,J},

                new Angle[]{R,R},
                new Angle[]{M,M},
                new Angle[]{C,C},
                new Angle[]{B,B},

                new Angle[]{D,D},
                new Angle[]{F,F},
                new Angle[]{Z,Z},
                new Angle[]{N,N}
        ));
    }};

    /**
     * 镜像对应表
     */
    public static final Map<Angle,Angle> mirrorMapping = new HashMap<Angle, Angle>(){{
        put(L,R);
        put(H,M);
        put(Q,C);
        put(G,B);

        put(U,D);
        put(T,F);
        put(E,Z);
        put(J,N);

        put(R,L);
        put(M,H);
        put(C,Q);
        put(B,G);

        put(D,U);
        put(F,T);
        put(Z,E);
        put(N,J);

    }};
}
