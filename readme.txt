bitlyminous (http://bitlyminous.appspot.com/) is a twitter bot that scans the user tweets for bitly urls and then sends related urls and tweets.
To begin getting url and tweet recommendations the user has to follow @bitlyminous. 
After that whenever the user posts a status containing a bitly url, the bot will first scan the url for malware and blacklisted sites using Google safe browsing API. This is an added protection as many malware sites not caught by bitly are caught by Google.
If the url is safe, the bot will try to send a related url recommendation to the user. It will also send related tweets to the user.
If the user signs up with bitlyminous using their twitter account, their friends timeline will also be scanned for malware and blacklisted sites and they will be alerted if any suspicious url is found.
The user can also sign up for delicious and digg accounts, after which every bitly url they post will be added to their digg and delicious account as well.
On each friday, the bot will send #FF recommendations of users that post similar urls/tweets.
Also on each friday the bot will send the statistics of all the urls posted by the user as a DM.

The bot also has an interactive mode which is activated whenever @bitlyminous is mentioned in the tweet. The following lists the features of the interactive mode with examples.

1. Subscribe to various tags to refine url and user recommendations.
@bitlyminous subscribe java mapreduce
2. Search nearby locations for keywords or find tips. The user must be sharing their location with twitter.
@bitlyminous nearby
@bitlyminous nearby coffee
3. Post a status update on behalf of the user which is based on the history of urls posted by the user.
@bitlyminous tweetforme
4. Search the web for any text/keyword.
@bitlyminous whats the best tutorial about java?

This application is open source with Apache 2.0 License. You can fork the github repo from https://github.com/nabeelmukhtar/bitlyminous.