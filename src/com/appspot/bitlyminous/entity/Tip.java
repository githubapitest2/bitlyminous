/*
 * Copyright 2010 Nabeel Mukhtar 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package com.appspot.bitlyminous.entity;


/**
 * The Class Tip.
 */
public class Tip {
    
    /** The created. */
    private String created;
    
    /** The distance. */
    private String distance;
    
    /** The id. */
    private String id;
    
    /** The stats. */
    private Tip.Stats stats;
    
    /** The status. */
    private String status;
    
    /** The text. */
    private String text;
    
    /** The venue. */
    private Venue venue;


    /**
     * Instantiates a new tip.
     */
    public Tip() {
    }

    /**
     * Gets the created.
     * 
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets the created.
     * 
     * @param created the new created
     */
    public void setCreated(String created) {
    	this.created = created;
    }

    /**
     * Gets the distance.
     * 
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Sets the distance.
     * 
     * @param distance the new distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id) {
    	this.id = id;
    }
    
    /**
     * Gets the stats.
     * 
     * @return the stats
     */
    public Tip.Stats getStats() {
        return stats;
    }
    
    /**
     * Sets the stats.
     * 
     * @param stats the new stats
     */
    public void setStats(Tip.Stats stats) {
    	this.stats = stats;
    }
    
    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(String status) {
    	this.status = status;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     * 
     * @param text the new text
     */
    public void setText(String text) {
    	this.text = text;
    }

    /**
     * Gets the venue.
     * 
     * @return the venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * Sets the venue.
     * 
     * @param venue the new venue
     */
    public void setVenue(Venue venue) {
    	this.venue = venue;
    }

    /**
     * The Class Stats.
     */
    public static class Stats {

        /** The done count. */
        private int doneCount;
        
        /** The todo count. */
        private int todoCount;
        
        /**
         * Instantiates a new stats.
         */
        public Stats() {
        }

        /**
         * Gets the done count.
         * 
         * @return the done count
         */
        public int getDoneCount() {
            return doneCount;
        }
        
        /**
         * Sets the done count.
         * 
         * @param doneCount the new done count
         */
        public void setDoneCount(int doneCount) {
        	this.doneCount = doneCount;
        }
        
        /**
         * Gets the todo count.
         * 
         * @return the todo count
         */
        public int getTodoCount() {
            return todoCount;
        }
        
        /**
         * Sets the todo count.
         * 
         * @param todoCount the new todo count
         */
        public void setTodoCount(int todoCount) {
        	this.todoCount = todoCount;
        }
    }
    
    /**
     * The Class Status.
     */
    public static class Status {

        /** The done. */
        private int done;
        
        /** The todo. */
        private int todo;
        
        /**
         * Instantiates a new status.
         */
        public Status() {
        }

        /**
         * Gets the done.
         * 
         * @return the done
         */
        public int getDone() {
            return done;
        }
        
        /**
         * Sets the done.
         * 
         * @param done the new done
         */
        public void setDone(int done) {
        	this.done = done;
        }
        
        /**
         * Gets the todo.
         * 
         * @return the todo
         */
        public int getTodo() {
            return todo;
        }
        
        /**
         * Sets the todo.
         * 
         * @param todo the new todo
         */
        public void setTodo(int todo) {
        	this.todo = todo;
        }
    }
}
