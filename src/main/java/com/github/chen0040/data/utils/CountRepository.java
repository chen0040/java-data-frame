package com.github.chen0040.data.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 18/8/15.
 */
public class CountRepository {
    private Map<String, CountRepository> chain = new HashMap<String, CountRepository>();
    private String eventName;
    private double storedValue;

    public void copy(CountRepository rhs){
        storedValue = rhs.storedValue;
        eventName = rhs.eventName;
        chain.clear();

        for(String key : rhs.chain.keySet()){
            chain.put(key, rhs.chain.get(key).makeCopy());
        }

    }


    public CountRepository makeCopy(){
        CountRepository clone = new CountRepository();
        clone.copy(this);

        return clone;
    }

    public CountRepository(){
        storedValue = 0;
    }

    public CountRepository(String evt){
        this.eventName = evt;
    }

    public String getEventName(){
        return eventName;
    }

    public double getStoredValue(){
        return storedValue;
    }

    public Map<String, CountRepository> getChain(){
        return chain;
    }

    public List<String> getSubEventNames(String... eventNames){
        return getSubEventNames(this, eventNames);
    }

    private List<String> getSubEventNames(CountRepository repo, String... eventNames){
        int eventNameCount = eventNames.length;
        if(eventNameCount == 0){
            List<String> events = new ArrayList<>();
            for(String eventName : repo.chain.keySet()){
                events.add(eventName);
            }
            return events;
        }

        if(repo.chain.containsKey(eventNames[0])) {
            repo = repo.chain.get(eventNames[0]);

            if (eventNameCount == 1) {
                return getSubEventNames(repo);
            } else {
                String[] subevents = new String[eventNameCount - 1];
                for (int j = 1; j < eventNameCount; ++j) {
                    subevents[j - 1] = eventNames[j];
                }
                return getSubEventNames(repo, subevents);
            }
        }else{
            return new ArrayList<>();
        }
    }

    public void addSupportCount(String... events){
        addSupportCount(1, events);
    }

    public void addSupportCount(double increment, String... events){
        int eventCount = events.length;
        if(eventCount==0){
            storedValue +=increment;
        }else {
            String evt = events[0];
            CountRepository c;
            if (chain.containsKey(evt)) {
                c = chain.get(evt);
            } else {
                c = new CountRepository(evt);
                chain.put(evt, c);
            }
            if (eventCount == 1) {
                c.addSupportCount(increment);
            } else {
                String[] subevents = new String[eventCount - 1];
                for (int j = 1; j < eventCount; ++j) {
                    subevents[j - 1] = events[j];
                }
                c.addSupportCount(increment, subevents);
            }
        }
    }

    public void setValue(double value, String... events){
        int eventCount = events.length;
        if(eventCount==0){
            storedValue =value;
        }else {
            String evt = events[0];
            CountRepository c;
            if (chain.containsKey(evt)) {
                c = chain.get(evt);
            } else {
                c = new CountRepository(evt);
                chain.put(evt, c);
            }
            if (eventCount == 1) {
                c.setValue(value);
            } else {
                String[] subevents = new String[eventCount - 1];
                for (int j = 1; j < eventCount; ++j) {
                    subevents[j - 1] = events[j];
                }
                c.setValue(value, subevents);
            }
        }
    }

    public double getProbability(String eventName){
        if(storedValue ==0) return 0;
        double count = getSupportCount(eventName);
        return count / storedValue;
    }

    // return the conditional probability of B given A happened
    public double getConditionalProbability(String eventA, String eventB){
        double givenCount = getSupportCount(eventA);
        if(givenCount == 0) return 0;
        return getSupportCount(eventA, eventB) / givenCount;

    }

    public double getSupportCount(String... events){
        int eventCount = events.length;
        if(eventCount == 0){
            return storedValue;
        }
        else{
            String evt = events[0];
            CountRepository c = null;
            if(chain.containsKey(evt)){
                c = chain.get(evt);
            }

            if(c == null) return 0;

            if(eventCount == 1){
                return c.getStoredValue();
            }else{
                String[] subevents = new String[eventCount-1];
                for(int j=1; j < eventCount; ++j){
                    subevents[j-1] = events[j];
                }
                return c.getSupportCount(subevents);
            }
        }
    }

    public double getValue(String... events){
        int eventCount = events.length;
        if(eventCount == 0){
            return storedValue;
        }
        else{
            String evt = events[0];
            CountRepository c = null;
            if(chain.containsKey(evt)){
                c = chain.get(evt);
            }

            if(c == null) return 0;

            if(eventCount == 1){
                return c.getValue();
            }else{
                String[] subevents = new String[eventCount-1];
                for(int j=1; j < eventCount; ++j){
                    subevents[j-1] = events[j];
                }
                return c.getSupportCount(subevents);
            }
        }
    }
}
