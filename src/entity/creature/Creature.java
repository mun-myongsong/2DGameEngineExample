package entity.creature;

import core.Position;
import core.Size;
import entity.Entity;
import input.Keyboard;
import map.GameMap;

/*
 * NESS LEVEL UP MAP
 * LV   |   NEED EXP
 * 1	|   0
 * 2	|   4
 * 3	|   17
 * 4	|   44
 * 5	|   109
 * 6	|   236
 * 7	|   449
 * 8	|   772
 * 9	|   1,229
 * 10	|   1,884
 * 11	|   2,611
 * 12	|   3,644
 * 13	|   4,877
 * 14	|   6,364
 * 15	|   8,129
 * 16	|   10,703
 * 17	|   13,241
 * 18	|   16,214
 * 19	|   19,673
 * 20	|   23,699
 * 21	|   28,253
 * 22	|   33,476
 * 23	|   39,389
 * 24	|   46,043
 * 25	|   53,489
 * 26	|   61,778
 * 27	|   70,961
 * 28	|   81,089
 * 29	|   92,213
 * 30	|   104,384
 * 31	|   117,653
 * 32	|   132,071
 * 33	|   147,689
 * 34	|   164,558
 * 35	|   182,729
 * 36	|   202,270
 * 37	|   223,249
 * 38	|   245,734
 * 39	|   269,793
 * 40	|   295,494
 * 41	|   322,905
 * 42	|   352,094
 * 43	|   383,129
 * 44	|   416,078
 * 45	|   451,009
 * 46	|   487,990
 * 47	|   527,089
 * 48	|   568,374
 * 49	|   611,913
 * 50	|   657,774
 * 51	|   706,025
 * 52	|   756,734
 * 53	|   809,969
 * 54	|   865,798
 * 55	|   924,289
 * 56	|   985,510
 * 57	|   1,049,529
 * 58	|   1,116,414
 * 59	|   1,186,233
 * 60	|   1,259,054
 * 61	|   1,335,030
 * 62	|   1,414,314
 * 63	|   1,497,059
 * 64	|   1,583,418
 * 65	|   1,673,544
 * 66	|   1,767,590
 * 67	|   1,865,709
 * 68	|   1,968,054
 * 69	|   2,074,778
 * 70	|   2,186,034
 * 71	|   2,301,975
 * 72	|   2,422,754
 * 73	|   2,548,524
 * 74	|   2,679,438
 * 75	|   2,815,649
 * 76	|   2,957,310
 * 77	|   3,104,574
 * 78	|   3,257,594
 * 79	|   3,416,523
 * 80	|   3,581,514
 * 81	|   3,752,720
 * 82	|   3,930,294
 * 83	|   4,114,389
 * 84	|   4,305,158
 * 85	|   4,502,754
 * 86	|   4,707,330
 * 87	|   4,919,039
 * 88	|   5,138,034
 * 89	|   5,364,468
 * 90	|   5,598,494
 * 91	|   5,840,265
 * 92	|   6,089,934
 * 93	|   6,347,654
 * 94	|   6,613,578
 * 95	|   6,887,859
 * 96	|   7,170,650
 * 97	|   7,462,104
 * 98	|   7,762,374
 * 99	|   8,071,613
 */
public abstract class Creature extends Entity {
    protected String name;
    protected int level;
    protected int maxhp;
    protected int maxmp;
    protected int hp;
    protected int mp;
    protected int exp;
    protected int nextlevel;
    protected int offens;
    protected int defens;
    protected int speed;
    protected int guts;
    protected int vitality;
    protected int iq;
    protected int luck;
    // TODO protected List<Magic> psis;

    public Creature(GameMap gameMap, String name, Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        super(gameMap, keyboard, position, size, fileName, tileWidth, tileHeight);
        this.name = name;
        this.level = 1;
        determiningStatus();
    }

    protected abstract void determiningStatus();
}
