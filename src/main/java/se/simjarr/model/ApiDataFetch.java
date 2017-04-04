package se.simjarr.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = jsonParser.parse(json).getAsJsonObject();

        nextChangeId.setNextId(gsonObject.get("next_change_id").getAsString());
        nextChangeIdRepository.save(nextChangeId);
        System.out.println("new id: " + nextChangeId.getNextId());
        JsonArray stashes = gsonObject.getAsJsonArray("stashes");
        for (int i = 0; i < stashes.size(); i++) {
            JsonObject object = stashes.get(i).getAsJsonObject();
            if (validateGsonStash(object)) {
                if (object.get("stashType").getAsString().equals("CurrencyStash")) {
                    CurrencyStash dbStash = currencyStashRepository.findByApiId(object.get("id").getAsString());
                    User dbUser = userRepository.findByUsername(object.get("accountName").getAsString());
                    if (dbStash == null) {
                        if(dbUser == null) {
                            dbUser = new User(object.get("accountName").getAsString());
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

    private boolean validateGsonStash(JsonObject jsonObject) {
        if(jsonObject.getAsJsonArray("items").size() <= 0) return false;
        return !(jsonObject.getAsJsonArray("items").get(0).getAsJsonObject().get("league").getAsString().isEmpty() || !jsonObject.get("public").getAsBoolean());
    }
}
