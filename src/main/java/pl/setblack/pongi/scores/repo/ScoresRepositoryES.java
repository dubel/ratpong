package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.airomem.core.Persistent;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.nio.file.Paths;

/**
 * Created by mike on 11.02.17.
 */
public class ScoresRepositoryES implements ScoresRepository {

    private final Persistent<ScoresRepositoryInMem> persistenceController;


    public ScoresRepositoryES(){
        this.persistenceController = Persistent.loadOptional(
                Paths.get("airomem/score"), () -> new ScoresRepositoryInMem()
        );

    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        this.persistenceController.execute(scoreRepo -> scoreRepo.registerScore(rec));
    }

    @Override
    public Option<UserScore> getUserScore(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
        return this.persistenceController.query(scoreRepo -> scoreRepo.getTopScores(limit));
    }
}
