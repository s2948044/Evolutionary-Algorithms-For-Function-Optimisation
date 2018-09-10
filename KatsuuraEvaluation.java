
/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  javabbob.JNIfgeneric
 *  javabbob.JNIfgeneric$Params
 *  org.vu.contest.ContestEvaluation
 */
import java.util.Properties;
import javabbob.JNIfgeneric;
import org.vu.contest.ContestEvaluation;

public class KatsuuraEvaluation implements ContestEvaluation {
    private static final int EVALS_LIMIT_ = 1000000;
    private static final int bbobid_ = 23;
    private static final double BASE_ = 7.73838444;
    private JNIfgeneric function_ = new JNIfgeneric();
    private double best_ = 0.0;
    private double target_ = 0.0;
    private int evaluations_ = 0;
    private String multimodal_ = "true";
    private String regular_ = "false";
    private String separable_ = "false";
    private String evals_ = Integer.toString(1000000);

    public KatsuuraEvaluation() {
        JNIfgeneric.Params params = new JNIfgeneric.Params();
        params.algName = "";
        params.comments = "";
        JNIfgeneric.makeBBOBdirs((String) "tmp", (boolean) true);
        this.function_.initBBOB(23, 1, 10, "tmp", params);
        this.target_ = this.function_.getFtarget();
    }

    public Object evaluate(Object object) {
        if (!(object instanceof double[])) {
            throw new IllegalArgumentException();
        }
        double[] arrd = (double[]) object;
        if (arrd.length != 10) {
            throw new IllegalArgumentException();
        }
        if (this.evaluations_ > 1000000) {
            return null;
        }
        double d = (this.function_.evaluate(arrd) - this.target_) / (7.73838444 - this.target_);
        double d2 = 10.0 * Math.exp(-5.0 * d);
        if (d2 > 10.0) {
            d2 = 10.0;
        } else if (d2 < 0.0) {
            d2 = 0.0;
        }
        if (d2 > this.best_) {
            this.best_ = d2;
        }
        ++this.evaluations_;
        return new Double(d2);
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