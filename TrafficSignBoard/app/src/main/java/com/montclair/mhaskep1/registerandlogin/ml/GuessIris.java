package com.montclair.mhaskep1.registerandlogin.ml;

import android.util.Log;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.Reader;

/**
 * This class loads Iris data model.
 * Once classifier is trained, its tested against test data to verify accuracy
 *
 */
public class GuessIris {

    public static Classifier knn;
    private static double accuracy;

    /**
     * Loads model; then trains and tests data.
     *
     * @param reader
     * @param test
     * @throws Exception
     */
    public static void loadResource(Reader reader, Reader test) throws Exception{

        /* Load a data set */

        Dataset data = FileHandler.load(reader, 4, ",");
        /*
         * Contruct a KNN classifier that uses 5 neighbors to make a decision.
         */
        knn = new KNearestNeighbors(5);
        knn.buildClassifier(data);

        /*
         * Load a data set for evaluation, this can be a different one, but we
         * will use the same one.
         */
        Dataset dataForClassification = FileHandler.load(test, 4, ",");
        /* Counters for correct and wrong predictions. */
        int correct = 0, wrong = 0;
        /* Classify all instances and check with the correct class values */
        for (Instance inst : dataForClassification) {
            Object predictedClassValue = knn.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue))
                correct++;
            else
                wrong++;
        }

        accuracy = 100 * correct/(correct+wrong);

        return;

    }

    /**
     * Predicts flower based on flower attribute.
     *
     * @param pl - Petal Length
     * @param pw - Petal Width
     * @param sl - Sepal Length
     * @param sw - Sepal Width
     * @return - Predicted flower with accuracy
     */
    public static String predict(double pl, double pw, double sl, double sw){
        double[] doubles = new double[4];
        doubles[0] = pl;
        doubles[1] = pw;
        doubles[2] = sl;
        doubles[3] = sw;

        Instance instance = new DenseInstance(doubles);

        Object predictedClassValue = knn.classify(instance);

        return String.format("%s ( Accuracy : %s)", predictedClassValue, accuracy);
    }
}
