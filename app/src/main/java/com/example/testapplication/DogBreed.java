package com.example.testapplication;

/**
 * Blueprint for DogType object, used in weight comparison
 */
public class DogBreed {
    private int[] mWeights;
    private int[] fWeights;
    private String breedName;

    private double sigma;
    private double mean;

    public DogBreed(String breedName, int[] mWeights, int[] fWeights){
        assert (mWeights.length == 2)&&(fWeights.length == 2) : "length of weight array must be 2";
        this.mWeights = mWeights;
        this.fWeights = fWeights;
        this.breedName = breedName;
    }

    /**
     * @return String representation of DogBreed
     */
    @Override
    public String toString(){
        return "\t" + breedName + "\n\t\t mWeights:" + mWeights[0] + "-" + mWeights[1] + "\n\t\t fWeights:" + fWeights[0] + "-" + fWeights[1];
    }
    public void print(){
        System.out.println(this.toString());
    }

    /**
     * returns weight plugged into bell curve function
     * represents "popularity" of weight
     * @param sex
     * @param weight
     * @return weight plugged into bell curve function
     */
    public double bellCurve(Sex sex, int weight){
        return (1 / (sigma * Math.sqrt(2 * Math.PI))) * Math.exp(-Math.pow(weight - mean, 2) / (2 * sigma * sigma));
    }

    /**
     * returns percentile of the dog weight
     * @param sex
     * @param weight
     * @return percentile of the dog weight based on this dogBreed
     */
    public double weightPercentile(Sex sex, int weight){
        double z;

        mean = switch (sex){
            case MALE:
                yield (mWeights[1]+mWeights[0])/2.0;
            case FEMALE:
                yield (fWeights[1]+fWeights[0])/2.0;
        };
        sigma = switch (sex){
            case MALE:
                yield (mWeights[1]-mWeights[0])/1.348;
            case FEMALE:
                yield (fWeights[1]-fWeights[0])/1.348;
        };

        z = (weight-mean)/ sigma;
        return zScoreToPercentile(z);
    }
    /**
     * Converts a z-score to a percentile using a numerical approximation
     * of the normal distribution CDF (via the error function).
     * @author ChatGPT
     */
    private static double zScoreToPercentile(double zScore) {
        return 0.5 * (1 + erf(zScore / Math.sqrt(2)));
    }

    /**
     * Approximation of mathmatical error function, porportional to the antiderivative of bell curve
     * @param x input to error function
     * @return double output of input x into approximation of error function
     */
    // Approximation of the error function (erf)
    private static double erf(double x) {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(x));

        double tau = t * Math.exp(-x * x - 1.26551223 +
                t * (1.00002368 +
                        t * (0.37409196 +
                                t * (0.09678418 +
                                        t * (-0.18628806 +
                                                t * (0.27886807 +
                                                        t * (-1.13520398 +
                                                                t * (1.48851587 +
                                                                        t * (-0.82215223 +
                                                                                t * 0.17087277)))))))));

        return x >= 0 ? 1 - tau : tau - 1;
    }

    /**
     * @return String name of dogBreed
     */
    public String getName() {
        return breedName;
    }

    /**
     * @return int[] female weight quartiles for this dogBreed
     */
    public int[] getfWeights() {
        return fWeights;
    }

    /**
     * @return int[] male weight quartiles for this dogBreed
     */
    public int[] getmWeights() {
        return mWeights;
    }

    /**
     * @param mWeights new male weight quartiles for this dogBreed
     */
    public void setmWeights(int[] mWeights) {
        this.mWeights = mWeights;
    }

    /**
     * @param fWeights new female weight quartiles for this dogBreed
     */
    public void setfWeights(int[] fWeights) {
        this.fWeights = fWeights;
    }

}
