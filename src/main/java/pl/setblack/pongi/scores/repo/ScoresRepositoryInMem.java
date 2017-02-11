package pl.setblack.pongi.scores.repo;

import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.PriorityQueue;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;
import pl.setblack.pongi.users.repo.UserData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class ScoresRepositoryInMem implements ScoresRepository, Serializable{


    private volatile List<UserScore> topScores = List.empty();
    private volatile HashMap<String, UserScore> usersScores = HashMap.empty();

    public ScoresRepositoryInMem() {

    }

    @Override
    public void registerScore(List<ScoreRecord> rec) {
        rec.forEach( record ->{
            usersScores.get(record.userId)
                    .orElse(Option.some(UserScore.emptyFor(record.userId)))
                    .forEach( oldRecord -> usersScores = usersScores.put(record.userId, oldRecord.add(record)));
        });
    }


    @Override
    public Option<UserScore> getUserScore(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserScore> getTopScores(int limit) {

       return this.usersScores.values()
               .sortBy(score -> score.totalScore)
               .reverse()
               .take(limit)
               .toList();


    }



}
