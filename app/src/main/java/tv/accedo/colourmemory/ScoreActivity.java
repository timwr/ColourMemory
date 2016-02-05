package tv.accedo.colourmemory;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ScoreActivity extends ListActivity {

    public static final String NAME = "name";
    public static final String SCORE = "score";

    private ArrayAdapter<String> scoreAdapter;
    private ScoreModel scoreModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                scoreModel = new ScoreModel();
                scoreModel.loadSync(ScoreActivity.this);
                if (getIntent().hasExtra(NAME)) {
                    String name = getIntent().getStringExtra(NAME);
                    int score = getIntent().getIntExtra(SCORE, 0);
                    scoreModel.addScore(name, score);
                    scoreModel.saveScores(ScoreActivity.this);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                updateScores();
            }
        }.execute();
    }

    private void updateScores() {
        ArrayList<String> scores = new ArrayList<>();
        for (Pair<String,Integer> score : scoreModel.getScores()) {
            scores.add("Name: " + score.first  + " (" + score.second + ")");

        }
        scoreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
        setListAdapter(scoreAdapter);
    }
}
