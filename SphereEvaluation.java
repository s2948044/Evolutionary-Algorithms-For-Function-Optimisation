
/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.vu.contest.ContestEvaluation
 */
import java.util.Properties;
import org.vu.contest.ContestEvaluation;

public class SphereEvaluation implements ContestEvaluation {
    private static final int EVALS_LIMIT_ = 10000;
    private static final double BASE_ = 11.5356;
    private static final double ftarget_ = 0.0;
    private double best_ = 0.0;
    private int evaluations_ = 0;
    private String multimodal_ = "false";
    private String regular_ = "true";
    private String separable_ = "true";
    private String evals_ = Integer.toString(10000);

    private double function(double[] arrd) {
        double d = 0.0;
        for (int i = 0; i < 10; ++i) {
            d += arrd[i] * arrd[i];
        }
        return d;
    }

    public Object evaluate(Object object) {
        if (!(object instanceof double[])) {
            throw new IllegalArgumentException();
        }
        double[] arrd = (double[]) object;
        if (arrd.length != 10) {
            throw new IllegalArgumentException();
        }
        if (this.evaluations_ > 10000) {
            return null;
        }
        double d = 10.0 - 10.0 * ((this.function(arrd) - 0.0) / 11.5356);
        if (d > this.best_) {
            this.best_ = d;
        }
        ++this.evaluations_;
        return new Double(d);
    }

    public Object getData(Object object) {
        return null;
    }

    public double getFinalResult() {
        return this.best_;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("Multimodal", this.multimodal_);
        properties.put("Regular", this.regular_);
        properties.put("Separable", this.separable_);
        properties.put("Evaluations", this.evals_);
        return properties;
    }
}