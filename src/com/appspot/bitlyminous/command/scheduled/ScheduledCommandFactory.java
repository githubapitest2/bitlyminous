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
package com.appspot.bitlyminous.command.scheduled;

import java.util.HashMap;
import java.util.Map;

import com.appspot.bitlyminous.constant.ParameterNames;


/**
 * A factory for creating ScheduledCommand objects.
 */
public class ScheduledCommandFactory {
	
	/** The Constant COMMANDS. */
	private static final Map<String, ScheduledCommand> COMMANDS = new HashMap<String, ScheduledCommand>();

	static {
		COMMANDS.put(ParameterNames.CHECK_TWEETS_SCHED, new CheckTweetsCommand());
		COMMANDS.put(ParameterNames.CHECK_MENTIONS_SCHED, new CheckMentionsCommand());
		COMMANDS.put(ParameterNames.SEARCH_TWEETS_SCHED, new SearchTweetsCommand());
		COMMANDS.put(ParameterNames.SEND_FF_SCHED, new SendFollowFridayCommand());
		COMMANDS.put(ParameterNames.SEND_POPULAR_LINKS_SCHED, new SendPopularLinksCommand());
		COMMANDS.put(ParameterNames.SEND_RELATED_TWEETS_SCHED, new SendRelatedTweetsCommand());
		COMMANDS.put(ParameterNames.SEND_STATISTICS_SCHED, new SendStatisticsCommand());
		COMMANDS.put(ParameterNames.UPDATE_GSB_DATA_SCHED, new UpdateSafeBrowsingDataCommand());
		COMMANDS.put(ParameterNames.RUN_MAINTENANCE_SCHED, new MaintenanceCommand());
	}
	
    /**
     * Instantiates a new scheduled command factory.
     */
    private ScheduledCommandFactory() {}

    /**
     * New instance.
     * 
     * @return the scheduled command factory
     */
    public static ScheduledCommandFactory newInstance() {
        return new ScheduledCommandFactory();
    }

    /**
     * Creates a new ScheduledCommand object.
     * 
     * @param action the action
     * 
     * @return the scheduled command
     */
    public ScheduledCommand createCommand(String action) {
		if (COMMANDS.containsKey(action)) {
			return COMMANDS.get(action);
		}
		throw new IllegalArgumentException("Unrecognizable command." + action);
    }
}
