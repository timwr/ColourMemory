package tv.accedo.colourmemory;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testScoreModel() {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.loadSync(getContext());
        scoreModel.addScore("Tim", 1);
        scoreModel.saveScores(getContext());
        assertTrue(scoreModel.getScores().size() > 0);
    }
}