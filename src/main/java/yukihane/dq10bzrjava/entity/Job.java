package yukihane.dq10bzrjava.entity;

/**
 *
 * @author yuki
 */
public enum Job {

    SENSI {
            @Override
            public int getId() {
                return 2;
            }

            @Override
            public String getText() {
                return "戦士";
            }
        }, SOU {
            @Override
            public int getId() {
                return 3;
            }

            @Override
            public String getText() {
                return "僧侶";
            }
        }, MAHOU {
            @Override
            public int getId() {
                return 4;
            }

            @Override
            public String getText() {
                return "魔法使い";
            }
        }, BUTOKA {
            @Override
            public int getId() {
                return 5;
            }

            @Override
            public String getText() {
                return "武闘家";
            }
        }, TOZOKU {
            @Override
            public int getId() {
                return 6;
            }

            @Override
            public String getText() {
                return "盗賊";
            }
        }, TABI {
            @Override
            public int getId() {
                return 7;
            }

            @Override
            public String getText() {
                return "旅芸人";
            }
        }, BATO {
            @Override
            public int getId() {
                return 8;
            }

            @Override
            public String getText() {
                return "パラディン";
            }
        }, PARA {
            @Override
            public int getId() {
                return 9;
            }

            @Override
            public String getText() {
                return "パラディン";
            }
        }, MASEN {
            @Override
            public int getId() {
                return 10;
            }

            @Override
            public String getText() {
                return "魔法戦士";
            }
        }, REN {
            @Override
            public int getId() {
                return 11;
            }

            @Override
            public String getText() {
                return "レンジャー";
            }
        }, KENJA {
            @Override
            public int getId() {
                return 12;
            }

            @Override
            public String getText() {
                return "賢者";
            }
        }, SUPA {
            @Override
            public int getId() {
                return 13;
            }

            @Override
            public String getText() {
                return "スーパースター";
            }
        }, MAMO {

            @Override
            public int getId() {
                return 14;
            }

            @Override
            public String getText() {
                return "まもの使い";
            }
        }, DOUGU {

            @Override
            public int getId() {
                return 15;
            }

            @Override
            public String getText() {
                return "どうぐ使い";
            }
        }, ODORI {

            @Override
            public int getId() {
                return 16;
            }

            @Override
            public String getText() {
                return "踊り子";
            }
        };

    public abstract int getId();

    public abstract String getText();

    @Override
    public String toString() {
        return getText();
    }
}
