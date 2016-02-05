package tv.accedo.colourmemory;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ScoreModel  {

    private static final String NUM_SCORES = "numScores";
    private static final String SCORES = "scores";

    private static final int MAX_SCORES = 10;
    private List<Pair<String,Integer>> scores;

    public List<Pair<String, Integer>> getScores() {
        return scores;
    }

    public void loadSync(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SCORES, Context.MODE_PRIVATE);
        int numScores = sharedPreferences.getInt(NUM_SCORES, 0);
        scores = new ArrayList<>();
        for (int l = 0; l < numScores; l++) {
            String name = sharedPreferences.getString(ScoreActivity.NAME + l, null);
            int score = sharedPreferences.getInt(ScoreActivity.SCORE + l, 0);
            scores.add(new Pair<String, Integer>(name, score));
        }
    }

    public void saveScores(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SCORES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUM_SCORES, scores.size());
        for (int l = 0; l < scores.size(); l++) {
            editor.putString(ScoreActivity.NAME + l, scores.get(l).first);
            editor.putInt(ScoreActivity.SCORE + l, scores.get(l).second);
        }
        editor.apply();
    }

    public void addScore(String name, int score) {
        int l;
        for (l = 0; l < scores.size(); l++) {
            if (score >= scores.get(l).second) {
                break;
            }
        }
        scores.add(l, new Pair<String, Integer>(name, score));
        if (scores.size() >= MAX_SCORES) {
            scores.remove(MAX_SCORES);
        }
    }
}
