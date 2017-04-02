package se.simjarr.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.simjarr.repository.CurrencyStashRepository;
import se.simjarr.repository.NextChangeIdRepository;
import se.simjarr.repository.UserRepository;

import java.io.IOException;

@Component
public class ApiDataFetch {

    private NextChangeId nextChangeId;
    private final CurrencyStashRepository currencyStashRepository;
    private final UserRepository userRepository;
    private final NextChangeIdRepository nextChangeIdRepository;

    @Autowired
    public ApiDataFetch(CurrencyStashRepository currencyStashRepository, UserRepository userRepository, NextChangeIdRepository nextChangeIdRepository) {
        this.currencyStashRepository = currencyStashRepository;
        this.userRepository = userRepository;
        this.nextChangeIdRepository = nextChangeIdRepository;

        nextChangeId = nextChangeIdRepository.findOne("next_id");
    }

    @Scheduled(fixedDelay = 10000)
    public void run() {
        Connection.Response response = null;
        try {
            response = Jsoup.connect("http://www.pathofexile.com/api/public-stash-tabs?id=" + nextChangeId.getNextId()).ignoreContentType(true).maxBodySize(0).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = response.body();
        JSONObject jsonObject = new JSONObject(json);

        nextChangeId.setNextId(jsonObject.getString("next_change_id"));
        nextChangeIdRepository.save(nextChangeId);
        System.out.println("new id: " + nextChangeId.getNextId());
        JSONArray stashes = jsonObject.getJSONArray("stashes");
        for (int i = 0; i < stashes.length(); i++) {
            JSONObject object = stashes.getJSONObject(i);
            if (validateJsonStash(object)) {
                if (object.getString("stashType").equals("CurrencyStash")) {
                    CurrencyStash dbStash = currencyStashRepository.findByApiId(object.getString("id"));
                    User dbUser = userRepository.findByUsername(object.getString("accountName"));
                    if (dbStash == null) {
                        if(dbUser == null) {
                            dbUser = new User(object.getString("accountName"));
                            userRepository.save(dbUser);
                        }
                        dbStash = new CurrencyStash(object, dbUser);
                        currencyStashRepository.save(dbStash);
                    } else {
                        dbStash.update(new CurrencyStash(object));
                        currencyStashRepository.save(dbStash);
                    }
                }
            }
        }
    }

    private boolean validateJsonStash(JSONObject jsonObject) {
        if(jsonObject.getJSONArray("items").isNull(0)) return false;
        return !(jsonObject.getJSONArray("items").getJSONObject(0).getString("league").isEmpty() || !jsonObject.getBoolean("public"));
    }
}
