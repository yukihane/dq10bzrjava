package yukihane.dq10bzrjava.entity;

/**
 *
 * @author yuki
 */
public enum Quality {

    ALL {
            @Override
            public String getText() {
                return "なんでも";
            }

            @Override
            public int getMin() {
                return 1;
            }

            @Override
            public int getMax() {
                return 3;
            }
        }, Q1 {

            @Override
            public String getText() {
                return "☆★★のみ";
            }

            @Override
            public int getMin() {
                return 1;
            }

            @Override
            public int getMax() {
                return 1;
            }
        }, Q1_OR_MORE {

            @Override
            public String getText() {
                return "☆★★以上";
            }

            @Override
            public int getMin() {
                return 1;
            }

            @Override
            public int getMax() {
                return 3;
            }
        }, Q2 {

            @Override
            public String getText() {
                return "☆☆★のみ";
            }

            @Override
            public int getMin() {
                return 2;
            }

            @Override
            public int getMax() {
                return 2;
            }

        }, Q2_OR_MORE {

            @Override
            public String getText() {
                return "☆☆★以上";
            }

            @Override
            public int getMin() {
                return 2;
            }

            @Override
            public int getMax() {
                return 3;
            }

        }, Q3 {

            @Override
            public String getText() {
                return "☆☆☆のみ";
            }

            @Override
            public int getMin() {
                return 3;
            }

            @Override
            public int getMax() {
                return 3;
            }

        };

    public abstract String getText();

    public abstract int getMin();

    public abstract int getMax();

    @Override
    public String toString() {
        return getText();
    }
}
