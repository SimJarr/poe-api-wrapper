package se.simjarr.model;

import org.json.JSONArray;
import org.json.JSONObject;
import se.simjarr.repository.UserRepository;

import javax.persistence.*;

@Entity
public class CurrencyStash extends AbstractEntity {

    private int alteration = 0;
    private int fusing = 0;
    private int alchemy = 0;
    private int chaos = 0;
    private int gemcutter = 0;
    private int exalt = 0;
    private int chromatic = 0;
    private int jeweller = 0;
    private int chance = 0;
    private int chisel = 0;
    private int scouring = 0;
    private int blessed = 0;
    private int regret = 0;
    private int regal = 0;
    private int divine = 0;
    private int vaal = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(unique = true)
    private String apiId;

    private String stashType;
    private String stashName;
    private String league;

    public CurrencyStash(JSONObject jsonObject) {
        stashType = "CurrencyStash";
        apiId = jsonObject.getString("id");
        stashName = jsonObject.getString("stash");

        league = jsonObject.getJSONArray("items").getJSONObject(0).getString("league");
        JSONArray items = jsonObject.getJSONArray("items");
        setCurrency(items);
    }

    public CurrencyStash(JSONObject jsonObject, User user) {
        this(jsonObject);
        setUser(user);
    }

    protected CurrencyStash() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void setCurrency(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            JSONObject object = items.getJSONObject(i);
            String currency = object.getString("typeLine");

            if (currency.equals("Orb of Alteration")) alteration = object.getInt("stackSize");
            if (currency.equals("Orb of Fusing")) fusing = object.getInt("stackSize");
            if (currency.equals("Orb of Alchemy")) alchemy = object.getInt("stackSize");
            if (currency.equals("Chaos Orb")) chaos = object.getInt("stackSize");
            if (currency.equals("Gemcutter's Prism")) gemcutter = object.getInt("stackSize");
            if (currency.equals("Exalted Orb")) exalt = object.getInt("stackSize");
            if (currency.equals("Chromatic Orb")) chromatic = object.getInt("stackSize");
            if (currency.equals("Jeweller's Orb")) jeweller = object.getInt("stackSize");
            if (currency.equals("Orb of Chance")) chance = object.getInt("stackSize");
            if (currency.equals("Cartographer's Chisel")) chisel = object.getInt("stackSize");
            if (currency.equals("Orb of Scouring")) scouring = object.getInt("stackSize");
            if (currency.equals("Blessed Orb")) blessed = object.getInt("stackSize");
            if (currency.equals("Orb of Regret")) regret = object.getInt("stackSize");
            if (currency.equals("Regal Orb")) regal = object.getInt("stackSize");
            if (currency.equals("Divine Orb")) divine = object.getInt("stackSize");
            if (currency.equals("Vaal Orb")) vaal = object.getInt("stackSize");
        }
    }

    /* GETTERS */

    public int getAlteration() {
        return alteration;
    }

    public int getFusing() {
        return fusing;
    }

    public int getAlchemy() {
        return alchemy;
    }

    public int getChaos() {
        return chaos;
    }

    public int getGemcutter() {
        return gemcutter;
    }

    public int getExalt() {
        return exalt;
    }

    public int getChromatic() {
        return chromatic;
    }

    public int getJeweller() {
        return jeweller;
    }

    public int getChance() {
        return chance;
    }

    public int getChisel() {
        return chisel;
    }

    public int getScouring() {
        return scouring;
    }

    public int getBlessed() {
        return blessed;
    }

    public int getRegret() {
        return regret;
    }

    public int getRegal() {
        return regal;
    }

    public int getDivine() {
        return divine;
    }

    public int getVaal() {
        return vaal;
    }

    public User getUser() {
        return user;
    }

    public String getStashType() {
        return stashType;
    }

    public String getApiId() {
        return apiId;
    }

    public String getStashName() {
        return stashName;
    }

    public String getLeague() {
        return league;
    }
}
